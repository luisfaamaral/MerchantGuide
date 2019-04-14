package com.merchantGuide;

import com.merchantGuide.util.MerchantGuideConversationHandlerConstants;

import java.util.*;
import java.util.regex.Matcher;

public class MerchantGuideConversationHandler {
    private Map<String, String> intergalaticNumberMap = new HashMap<>();
    private Map<String, Double> intergalaticMaterialMap = new HashMap<>();

    public Map<String, String> getIntergalaticNumberMap() {
        return intergalaticNumberMap;
    }
    public Map<String, Double> getIntergalaticMaterialMap() {
        return intergalaticMaterialMap;
    }

    private MerchantGuideTranslator merchantGuideTranslator;

    /*
     * Constructors
     */
    public MerchantGuideConversationHandler() {
        this.merchantGuideTranslator = new MerchantGuideTranslator();
    }
    public MerchantGuideConversationHandler(MerchantGuideTranslator merchantGuideTranslator) {
        this.merchantGuideTranslator = merchantGuideTranslator;
    }

    /*
     * Translate money conversion
     * NULL return means the conversion was OK
     */
    public String doTranslateMoneyConversion(String s) {
        String convertedMoney;
        StringBuilder completedConvertedMoneyString = new StringBuilder();
        Set<String> unknowVariablesSet = new HashSet<>();

        // translate intergalatic material
        Matcher m = MerchantGuideConversationHandlerConstants.MONEY_CONVERSION_WITH_VARIABLE_PATTERN.matcher(s);
        if (m.find()) {
            for (String thisGalaxayMoney : m.group(1).split("\\ ")) {
                convertedMoney = intergalaticNumberMap.get(thisGalaxayMoney);

                if (null == convertedMoney)
                    unknowVariablesSet.add(thisGalaxayMoney);
                else
                    completedConvertedMoneyString.append(convertedMoney);

                if (unknowVariablesSet.size() > 1)
                    return MerchantGuideConversationHandlerConstants.TOO_MANY_VARIABLES;
            }

            int totalValue = Integer.parseInt(m.group(2));

            int romanValue = merchantGuideTranslator.translateRomanString(completedConvertedMoneyString.toString());

            for (String material : unknowVariablesSet)
                intergalaticMaterialMap.put(material, (double) totalValue / romanValue);

            return MerchantGuideConversationHandlerConstants.MATERIAL_ADDED;
        }

        // translate intergalatic Money
        m = MerchantGuideConversationHandlerConstants.MONEY_CONVERSION_PATTERN.matcher(s);
        if (m.find()) {
            intergalaticNumberMap.put(m.group(1), m.group(2));

            return MerchantGuideConversationHandlerConstants.MONEY_ADDED;
        }

        return MerchantGuideConversationHandlerConstants.DEFAULT_NO_IDEA_ANSWER;
    }

    public String doTranslateQuestion(String s) {
        if (intergalaticNumberMap.size() == 0)
            return MerchantGuideConversationHandlerConstants.NO_DEFINITION;

        String translatedQuestion;

        // how much question
        Matcher m = MerchantGuideConversationHandlerConstants.QUESTION_HOW_MUCH_PATTERN.matcher(s);
        if (m.find()) {
            translatedQuestion = m.group(1);
            return String.format(MerchantGuideConversationHandlerConstants.DEFAULT_HOW_MUCH_ANSWER, translatedQuestion, calculateFinalValue(translatedQuestion));
        }

        // how many question
        m = MerchantGuideConversationHandlerConstants.QUESTION_HOW_MANY_CREDITS_PATTERN.matcher(s);
        if (m.find()) {
            translatedQuestion = m.group(1);
            return String.format(MerchantGuideConversationHandlerConstants.DEFAULT_HOW_MANY_CREDITS_ANSWER, translatedQuestion, calculateFinalValue(translatedQuestion));
        }

        return MerchantGuideConversationHandlerConstants.DEFAULT_NO_IDEA_ANSWER;
    }

    private Double calculateFinalValue(String moneyString) {
        String galaxyMoney;
        Double galaxyMaterialValue = null;

        StringBuilder finalGalaxyMoney = new StringBuilder();

        if (null != moneyString) {
            for (String s : moneyString.split("\\ ")) {
                galaxyMoney = intergalaticNumberMap.get(s);

                if (null != galaxyMoney)
                    finalGalaxyMoney.append(galaxyMoney);
                else {
                    galaxyMaterialValue = intergalaticMaterialMap.get(s);
                }
            }
        }

        int total = merchantGuideTranslator.translateRomanString(finalGalaxyMoney.toString());

        return galaxyMaterialValue == null ? total : total * galaxyMaterialValue;
    }
}
