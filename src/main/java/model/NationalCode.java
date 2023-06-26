package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * National code POJO class
 *
 * @author Amir Mohammad Hl
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
public class NationalCode {

    private String code;

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
