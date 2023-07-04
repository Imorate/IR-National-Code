import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.NationalCode;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * National code detail provider
 *
 * @author Amir Mohammad Hl
 * @version 1.0
 */
public class NationalCodeDetailProvider {

    /**
     * Provides province and city from top most left three digits
     *
     * @param nationalCode <a href="#{@link}">{@link NationalCode}</a>
     * @return Optional <a href="#{@link}">{@link NationalCode}</a> if details is exists. Otherwise, returns empty <a href="#{@link}">{@link Optional}</a>
     * @throws IOException Throws if <code>details.json</code> isn't readable
     */
    public Optional<NationalCode> getDetail(NationalCode nationalCode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("details.json");
        TypeReference<Map<String, List<String>>> typeReference = new TypeReference<Map<String, List<String>>>() {
        };
        Map<String, List<String>> detailsMap = objectMapper.readValue(inputStream, typeReference);
        String firstThreeDigits = nationalCode.getCode().substring(0, 3);
        List<String> details = detailsMap.get(firstThreeDigits);
        if (details == null) {
            return Optional.empty();
        }
        nationalCode.setProvince(details.get(0));
        nationalCode.setCity(details.get(1));
        return Optional.of(nationalCode);
    }

}
