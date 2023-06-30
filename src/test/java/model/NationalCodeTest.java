package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("National code test")
class NationalCodeTest {

    private final NationalCode nationalCode = new NationalCode("0123456789");

    @Test
    @DisplayName("National code fields")
    void shouldAllFieldsNullExceptCode() {
        assertThat(nationalCode.getCode()).isNotNull();
        assertThat(nationalCode.getProvince()).isNull();
        assertThat(nationalCode.getCity()).isNull();
    }

    @Test
    @DisplayName("National code control digit")
    void shouldReturnNationalCodeControlDigit() {
        assertThat(nationalCode.getControlDigit()).isEqualTo(9);
    }

}