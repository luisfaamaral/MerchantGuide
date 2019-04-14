package com.merchantGuide;

import com.merchantGuide.util.MerchantGuideConstants;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MerchantGuideTest {
    MerchantGuideTranslator merchantGuideTranslator;
    MerchantGuideConversationHandler merchantGuideConversationHandler;

    @Before
    public void setup() {
        merchantGuideTranslator = new MerchantGuideTranslator();
        merchantGuideConversationHandler = new MerchantGuideConversationHandler();
    }

    @Test
    public void RomanConversionSimpleTest() {
        assertEquals(10, MerchantGuideConstants.romanNumeralEnum.valueOf("X").getValue());
        assertEquals(500, MerchantGuideConstants.romanNumeralEnum.D.getValue());
    }

    @Test
    public void translateRomanValueWithNullTest() {
        assertEquals(0, merchantGuideTranslator.translateRomanString(null));
    }

    @Test
    public void translateRomanValueSimpleTest() {
        assertEquals(50, merchantGuideTranslator.translateRomanString("L"));
        assertEquals(2, merchantGuideTranslator.translateRomanString("II"));
        assertEquals(12, merchantGuideTranslator.translateRomanString("XII"));
    }

    @Test
    public void translateRomanValueWithMinusExpressionTest() {
        assertEquals(9, merchantGuideTranslator.translateRomanString("IX"));
        assertEquals(40, merchantGuideTranslator.translateRomanString("XL"));
        assertEquals(98, merchantGuideTranslator.translateRomanString("XCVIII"));
        assertEquals(2006, merchantGuideTranslator.translateRomanString("MMVI"));
        assertEquals(1944, merchantGuideTranslator.translateRomanString("MCMXLIV"));
        assertEquals(1903, merchantGuideTranslator.translateRomanString("MCMIII"));
    }

    @Test
    public void translateRomanValueWithInvalidExpression4thInARowTest() {
        assertEquals(-1, merchantGuideTranslator.translateRomanString("IIII"));
    }

    @Test
    public void translateRomanValueWithInvalidExpressionSubtractFromMuchHigherValueTest() {
        assertEquals(-1, merchantGuideTranslator.translateRomanString("IC"));
        assertEquals(-1, merchantGuideTranslator.translateRomanString("XD"));
        assertEquals(-1, merchantGuideTranslator.translateRomanString("VX"));
        assertEquals(-1, merchantGuideTranslator.translateRomanString("LC"));
        assertEquals(-1, merchantGuideTranslator.translateRomanString("DM"));
    }

    @Test
    public void understandMoneyConversion() {
        merchantGuideConversationHandler.doTranslateMoneyConversion("glob is I");
        merchantGuideConversationHandler.doTranslateMoneyConversion("prok is V");
        merchantGuideConversationHandler.doTranslateMoneyConversion("pish is X");
        merchantGuideConversationHandler.doTranslateMoneyConversion("tegj is L");

        assertEquals(true, merchantGuideConversationHandler.getIntergalaticNumberMap().containsKey("glob"));
        assertEquals("I", merchantGuideConversationHandler.getIntergalaticNumberMap().get("glob"));
        assertEquals(true, merchantGuideConversationHandler.getIntergalaticNumberMap().containsKey("prok"));
        assertEquals("V", merchantGuideConversationHandler.getIntergalaticNumberMap().get("prok"));
        assertEquals(true, merchantGuideConversationHandler.getIntergalaticNumberMap().containsKey("pish"));
        assertEquals("X", merchantGuideConversationHandler.getIntergalaticNumberMap().get("pish"));
        assertEquals(true, merchantGuideConversationHandler.getIntergalaticNumberMap().containsKey("tegj"));
        assertEquals("L", merchantGuideConversationHandler.getIntergalaticNumberMap().get("tegj"));
    }

    @Test
    public void understandQuestionHowMuchWithoutAnyMoneyConversion() {
        assertEquals("There is no conversion definition for this galaxy!!!", merchantGuideConversationHandler.doTranslateQuestion("How much is glob prok?"));
    }

    @Test
    public void understandQuestionHowMuch() {
        merchantGuideConversationHandler.doTranslateMoneyConversion("glob is I");

        assertEquals("glob is 1", merchantGuideConversationHandler.doTranslateQuestion("how much is glob ?"));
    }

    @Test
    public void understandMoneyConversionWithVariableMoreThanOneVariable() {
        assertEquals("Too many unknown variables!!!", merchantGuideConversationHandler.doTranslateMoneyConversion("glob glob Silver is 34 Credits"));
    }

    @Test
    public void understandMoneyConversionWithVariableVariable() {
        merchantGuideConversationHandler.doTranslateMoneyConversion("glob is I");
        merchantGuideConversationHandler.doTranslateMoneyConversion("glob glob Silver is 34 Credits");

        assertEquals(true, merchantGuideConversationHandler.getIntergalaticMaterialMap().containsKey("Silver"));
        assertEquals(17, merchantGuideConversationHandler.getIntergalaticMaterialMap().get("Silver").intValue());
    }

    @Test
    public void understandQuestionHowManyCredits() {
        merchantGuideConversationHandler.doTranslateMoneyConversion("glob is I");
        merchantGuideConversationHandler.doTranslateMoneyConversion("prok is V");
        merchantGuideConversationHandler.doTranslateMoneyConversion("glob glob Silver is 34 Credits");

        assertEquals("prok Silver is 85 Credits", merchantGuideConversationHandler.doTranslateQuestion("how many Credits is prok Silver ?"));
    }

    @Test
    public void finalTest() {
        merchantGuideConversationHandler.doTranslateMoneyConversion("glob is I");
        merchantGuideConversationHandler.doTranslateMoneyConversion("prok is V");
        merchantGuideConversationHandler.doTranslateMoneyConversion("pish is X");
        merchantGuideConversationHandler.doTranslateMoneyConversion("tegj is L");
        merchantGuideConversationHandler.doTranslateMoneyConversion("glob glob Silver is 34 Credits");
        merchantGuideConversationHandler.doTranslateMoneyConversion("glob prok Gold is 57800 Credits");
        merchantGuideConversationHandler.doTranslateMoneyConversion("pish pish Iron is 3910 Credits");

        assertEquals("pish tegj glob glob is 42", merchantGuideConversationHandler.doTranslateQuestion("how much is pish tegj glob glob ?"));
        assertEquals("glob prok Silver is 68 Credits", merchantGuideConversationHandler.doTranslateQuestion("how many Credits is glob prok Silver ?"));
        assertEquals("glob prok Gold is 57800 Credits", merchantGuideConversationHandler.doTranslateQuestion("how many Credits is glob prok Gold ?"));
        assertEquals("glob prok Iron is 782 Credits", merchantGuideConversationHandler.doTranslateQuestion("how many Credits is glob prok Iron ?"));
        assertEquals("I have no idea what you are talking about", merchantGuideConversationHandler.doTranslateQuestion("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"));
    }
}
