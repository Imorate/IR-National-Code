import exception.BadCharacterException;
import exception.InvalidLengthException;
import model.NationalCode;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * National code validator
 *
 * @author Amir Mohammad Hl
 * @version 1.0
 */
public class NationalCodeValidator {

    /**
     * Validates national code for valid format and characters
     *
     * @param code National code
     * @return Optional of <a href="#{@link}">{@link NationalCode}</a> if its valid or empty <a href="#{@link}">{@link Optional}</a> on invalid format
     * @throws InvalidLengthException If the length is invalid
     * @throws BadCharacterException If the bad characters exists
     */
    public Optional<NationalCode> validate(String code) throws InvalidLengthException, BadCharacterException {
        NationalCode nationalCode = new NationalCode(code);
        if (nationalCode.getCode().matches(".*\\D+.*")) {
            throw new BadCharacterException("Bad character(s) in national code");
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

    /**
     * Checks that if national code length is 10
     *
     * @param nationalCode <a href="#{@link}">{@link NationalCode}</a>
     * @return Returns true if length is valid
     */
    public boolean hasValidLength(NationalCode nationalCode) {
        return nationalCode.getCode().matches("^\\d{10}$");
    }

}
