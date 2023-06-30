import model.NationalCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("National code detail provider test")
class NationalCodeDetailProviderTest {

    private NationalCodeDetailProvider nationalCodeDetailProvider;

    @BeforeEach
    void setUp() {
        nationalCodeDetailProvider = new NationalCodeDetailProvider();
    }

    @DisplayName("Provide details with valid national codes")
    @ParameterizedTest(name = "{index} National code {0} - Province {1} - City {2}")
    @CsvSource({
            "0036332119, تهران ,تهران مرکزی",
            "0753265346, خراسان شمالی, جاجرم",
            "0622085417, مرکزی, سربند"
    })
    void shouldReturnDetailsOnValidNationalCode(String code, String province, String city) throws IOException {
        NationalCode nationalCode = new NationalCode(code);
        Optional<NationalCode> nationalCodeOptional = nationalCodeDetailProvider.getDetail(nationalCode);
        assertThat(nationalCodeOptional.isPresent()).isTrue();
        assertThat(nationalCodeOptional.get().getCode()).isEqualTo(code);
        assertThat(nationalCodeOptional.get().getProvince()).isEqualTo(province);
        assertThat(nationalCodeOptional.get().getCity()).isEqualTo(city);
    }

    @DisplayName("Provide no details with valid national codes")
    @ParameterizedTest
    @ValueSource(strings = {"0147880671", "0809437570", "0109319370"})
    void shouldNotReturnDetailsOnValidNationalCode(String code) throws IOException {
        NationalCode nationalCode = new NationalCode(code);
        Optional<NationalCode> nationalCodeOptional = nationalCodeDetailProvider.getDetail(nationalCode);
        assertThat(nationalCodeOptional.isPresent()).isFalse();
    }

}