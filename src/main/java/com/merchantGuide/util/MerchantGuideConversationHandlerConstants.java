package com.merchantGuide.util;

import java.util.regex.Pattern;

public class MerchantGuideConversationHandlerConstants {
    public static final String NO_DEFINITION = "There is no conversion definition for this galaxy!!!";
    public static final String TOO_MANY_VARIABLES = "Too many unknown variables!!!";
    public static final String MATERIAL_ADDED = "Ok! Intergalatic material Information added to the system.";
    public static final String MONEY_ADDED = "Ok! Intergalatic money information has been added to the system.";

    public static final String DEFAULT_HOW_MUCH_ANSWER = "%s is %.0f";
    public static final String DEFAULT_HOW_MANY_CREDITS_ANSWER = "%s is %.0f Credits";
    public static final String DEFAULT_NO_IDEA_ANSWER ="I have no idea what you are talking about";

    public static final Pattern MONEY_CONVERSION_PATTERN = Pattern.compile("(.*)\\ is\\ (.*)$");
    public static final Pattern MONEY_CONVERSION_WITH_VARIABLE_PATTERN = Pattern.compile("(.*)\\ is\\ (.*)\\ Credits$");

    public static final Pattern QUESTION_HOW_MUCH_PATTERN = Pattern.compile("how\\ much\\ is\\ (.*)\\ \\?$");
    public static final Pattern QUESTION_HOW_MANY_CREDITS_PATTERN = Pattern.compile("how\\ many\\ Credits\\ is\\ (.*)\\ \\?$");
}
