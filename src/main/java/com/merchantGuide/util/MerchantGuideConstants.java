package com.merchantGuide.util;

import java.util.Arrays;
import java.util.List;

public class MerchantGuideConstants {
    public final static char defaultControl4thCharacter = '#';

    public final static List romansCannotBeSubctracted = Arrays.asList(romanNumeralEnum.D.toString(), romanNumeralEnum.L.toString(), romanNumeralEnum.V.toString());

    public enum romanNumeralEnum {
        I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

        private int value;

        romanNumeralEnum(int val) {
            this.value = val;
        }

        public int getValue(){
            return value;
        }

    }
}
