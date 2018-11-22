package com.quickcheck;

import org.json.JSONException;
import org.json.JSONObject;

public class GetDataFromApp {

    public static String sendData(String UserId, String AppId, String SponsorBank, String DebitType, String Frequency, String ToDebit,
                                  String BankAccountNumber, String BankName, String IFSC, String Amount, String UtilityCode,
                                  String MICR, String ReferenceNumber, String PhoneNumber, String EmailID, String FromDate,
                                  String ToDate, String AccountHolder, String DateOfMandate, String MandateMode) {

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("UserId", UserId);
            jsonObj.put("AppId", AppId);
            jsonObj.put("SponsorBank", SponsorBank);
            jsonObj.put("DebitType", DebitType);
            jsonObj.put("Frequency", Frequency);
            jsonObj.put("ToDebit", ToDebit);
            jsonObj.put("BankAccountNumber", BankAccountNumber);
            jsonObj.put("BankName", BankName);
            jsonObj.put("IFSC", IFSC);
            jsonObj.put("Amount", Amount);
            jsonObj.put("UtilityCode", UtilityCode);
            jsonObj.put("MICR", MICR);
            jsonObj.put("ReferenceNumber", ReferenceNumber);
            jsonObj.put("PhoneNumber", PhoneNumber);
            jsonObj.put("EmailID", EmailID);
            jsonObj.put("FromDate", FromDate);
            jsonObj.put("ToDate", ToDate);
            jsonObj.put("AccountHolder", AccountHolder);
            jsonObj.put("DateOfMandate", DateOfMandate);
            jsonObj.put("MandateMode", MandateMode);
            return jsonObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
