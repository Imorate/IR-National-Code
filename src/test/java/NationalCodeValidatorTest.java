import exception.InvalidFormatException;
import exception.InvalidLengthException;
import model.NationalCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("National code validator test")
class NationalCodeValidatorTest {

    private NationalCodeValidator nationalCodeValidator;

    @BeforeEach
    void setUp() {
        nationalCodeValidator = new NationalCodeValidator();
    }

    @DisplayName("Valid national codes length")
    @ParameterizedTest
    @ValueSource(strings = {
            "0475356012", "0145130223", "0818138742", "9128204689", "4415882791", "3200010010", "7651000005"
    })
    void shouldReturnTrueOnValidNationalCodeLength(String code) {
        NationalCode nationalCode = new NationalCode(code);

        assertThat(nationalCodeValidator.hasValidLength(nationalCode)).isTrue();
    }

    @DisplayName("Invalid national codes length")
    @ParameterizedTest
    @ValueSource(strings = {
            "98765432101", "123456789", "3541156", "123", "0", "", " "
    })
    void shouldReturnFalseOnValidNationalCodeLength(String code) {
        NationalCode nationalCode = new NationalCode(code);

        assertThat(nationalCodeValidator.hasValidLength(nationalCode)).isFalse();
    }

    @DisplayName("Invalid national codes with non-numeric characters")
    @ParameterizedTest
    @ValueSource(strings = {"1234567a", "a", "1234567*", "#123456", "987654@123"})
    void shouldThrowExceptionOnNonNumericalCharacters(String code) {
        assertThatThrownBy(() -> nationalCodeValidator.validate(code)).isInstanceOf(InvalidFormatException.class)
                .hasMessage("Invalid character(s) in national code");
    }

    @DisplayName("Invalid national codes with invalid length")
    @ParameterizedTest
    @ValueSource(strings = {"123456789", "1", "123456789321"})
    void shouldThrowExceptionOnInvalidLength(String code) {
        assertThatThrownBy(() -> nationalCodeValidator.validate(code)).isInstanceOf(InvalidLengthException.class)
                .hasMessage("Invalid national code length. A valid national code length is 10");
    }

    @DisplayName("Valid national codes")
    @ParameterizedTest
    @ValueSource(strings = {"0475356012", "0145130223", "0818138742", "9128204689",
            "4415882791", "3200010010", "7651000005"})
    void shouldReturnOptionalNationalCode(String code) {
        Optional<NationalCode> nationalCodeOptional = nationalCodeValidator.validate(code);
        NationalCode nationalCode = new NationalCode(code);

        assertThat(nationalCodeOptional.isPresent()).isTrue();
        assertThat(nationalCodeOptional.get().getCode()).isEqualTo(nationalCode.getCode());
    }

    @DisplayName("Invalid national codes")
    @ParameterizedTest
    @ValueSource(strings = {"8469081842", "2243327478", "1749434491", "3964924373",
            "8133073115", "7741881573", "2446150066"})
    void shouldReturnEmptyOptional(String code) {
        Optional<NationalCode> nationalCodeOptional = nationalCodeValidator.validate(code);

        assertThat(nationalCodeOptional.isPresent()).isFalse();
    }

}