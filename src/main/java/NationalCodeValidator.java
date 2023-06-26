import exception.InvalidFormatException;
import exception.InvalidLengthException;
import model.NationalCode;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * National code validator
 *
 * @author Amir Mohammad Hl
 * @version 1.0.0
 */
public class NationalCodeValidator {

    public Optional<NationalCode> validate(String code) throws InvalidLengthException, InvalidFormatException {
        NationalCode nationalCode = new NationalCode(code);
        if (nationalCode.getCode().matches(".*\\D+.*")) {
            throw new InvalidFormatException("Invalid character(s) in national code");
        }
        if (!hasValidLength(nationalCode)) {
            throw new InvalidLengthException("Invalid national code length. A valid national code length is 10");
        }
        int controlDigit = nationalCode.getControlDigit();
        int sum = IntStream.range(0, 9)
                .boxed()
                .reduce(0, (partialResult, index) -> {
                    int digit = Character.getNumericValue(nationalCode.getCode().charAt(index));
                    return partialResult + digit * (10 - index);
                }, Integer::sum);
        int remainder = sum % 11;
        boolean remainderLessThanTwo = remainder < 2 && controlDigit == remainder;
        boolean remainderEqualAndMoreThanTwo = remainder >= 2 && remainder + controlDigit == 11;
        if (remainderLessThanTwo || remainderEqualAndMoreThanTwo) {
            return Optional.of(nationalCode);
        }
        return Optional.empty();
    }

    public boolean hasValidLength(NationalCode nationalCode) {
        return nationalCode.getCode().matches("^\\d{10}$");
    }

}
