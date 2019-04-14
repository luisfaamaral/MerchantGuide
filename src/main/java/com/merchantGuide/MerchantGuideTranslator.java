package com.merchantGuide;

import com.merchantGuide.util.MerchantGuideConstants;

import java.util.ArrayList;
import java.util.List;

public class MerchantGuideTranslator {
    /*
     * Return the integer value from a given roman string
     */
    public int translateRomanString(String romanString) {
        if (null == romanString || romanString.length() == 0)
            return 0;

        if (romanString.length() == 1)
            return getRomanValue(romanString);

        int total = 0;
        int i = 0;
        List<Character> control4thCharacterList = new ArrayList<>();

        do {
            char roman1Char = romanString.charAt(i);

            if (!control4thSequentialCharacter(roman1Char, control4thCharacterList))
                return -1;

            int roman1 = getRomanValue(roman1Char);

            // last character of the string - just sum up
            if (i == romanString.length() - 1) {
                total += roman1;
            } else {
                char roman2Char = romanString.charAt(i + 1);
                int roman2 = getRomanValue(roman2Char);

                if (roman1 < roman2) { // left is lower than right - subtract both and go for next
                    if (!controlSubtractFromMuchHigherOrInvalidValue(roman1Char, roman2Char))
                        return -1;

                    total += roman2 - roman1;
                    i++;

                    // clear the list as characters are different
                    control4thCharacterList.clear();
                } else {
                    total += roman1;
                }
            }

            i++;
        } while (i < romanString.length());

        return total;
    }

    /*
     * Validate if:
     * "I" can be subtracted from "V" and "X" only.
     * "X" can be subtracted from "L" and "C" only.
     * "C" can be subtracted from "D" and "M" only
     * "V", "L", and "D" can never be subtracted.
     */
    private boolean controlSubtractFromMuchHigherOrInvalidValue(char roman1Char, char roman2Char) {
        return !MerchantGuideConstants.romansCannotBeSubctracted.contains(String.valueOf(roman1Char)) && getRomanOrdinal(roman2Char) - getRomanOrdinal(roman1Char) <= 2;

    }

    /*
     * Get ordinal value from the specific roman
     */
    private int getRomanOrdinal(char roman) {
        return MerchantGuideConstants.romanNumeralEnum.valueOf(String.valueOf(roman)).ordinal();
    }

    /*
     * Validate 4th sequential character
     */
    private boolean control4thSequentialCharacter(char romanChar, List<Character> control4thCharacterList) {
        // add value if list is empty
        if (control4thCharacterList.size() == 0) {
            control4thCharacterList.add(romanChar);

            return true;
        }

        if (control4thCharacterList.get(0).equals(romanChar)) {
            // 4th time same character - send error
            if (control4thCharacterList.size() == 3)
                return false;
            else
                control4thCharacterList.add(romanChar);
        } else {
            // different character - clear the list
            control4thCharacterList.clear();
        }

        return true;
    }

    /*
     * Get roman value from a String
     */
    private int getRomanValue(String romanString) {
        return MerchantGuideConstants.romanNumeralEnum.valueOf(romanString).getValue();
    }

    /*
     * Get roman value from a Character
     */
    private int getRomanValue(char romanChar) {
        return getRomanValue(String.valueOf(romanChar));
    }
}
