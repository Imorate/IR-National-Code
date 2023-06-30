package model;

import lombok.Data;
import lombok.NonNull;

/**
 * National code POJO class
 *
 * @author Amir Mohammad Hl
 * @version 1.0.0
 */
@Data
public class NationalCode {

    /**
     * National code
     */
    @NonNull
    private String code;

    /**
     * National code province
     */
    private String province;

    /**
     * National code city
     */
    private String city;

    /**
     * The last digit of national code
     *
     * @return Control digit
     */
    public int getControlDigit() {
        char ch = code.charAt(code.length() - 1);
        return Character.getNumericValue(ch);
    }

}
