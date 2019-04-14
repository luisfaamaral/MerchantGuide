package com.merchantGuide;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MerchantGuide {
    public static void main(String[] args) {
        System.out.println("Please input the notes of conversation:\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = null;

        MerchantGuideConversationHandler merchantGuideConversationHandler = new MerchantGuideConversationHandler();

        try {
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.contains("?"))
                        System.out.println(merchantGuideConversationHandler.doTranslateQuestion(line));
                    else {
                        System.out.println(merchantGuideConversationHandler.doTranslateMoneyConversion(line));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid entry.");
        }
    }
}
