package com.quickcheck.webservice;

public class Serverfuction {
  // public static final String Baseurl = "http://192.168.1.246:805/Service1.svc/";//local url
   // public static final String Baseurl = "http://180.179.221.43:81/Service1.svc/";//yoeki url
   public static final String Baseurl = "http://180.179.221.55:8081/Service1.svc/";// quickcheck
    //login id
    public static final String login = Baseurl + "LoginUser";
    //forgot password
    public static final String forgotpassword = Baseurl + "SendEmail";
    //valmandate
    public static final String valmandate = Baseurl + "valAccount";
    //getmandate
    public static final String gatmandate = Baseurl + "GetMandate";
    //upload image
    public static final String uploadimage = Baseurl + "uploadImage";
    //UpdateToDate
    public static final String UpdateToDate = Baseurl + "UpdateToDate";
    //getmandate datewise
    public static final String getmandatedatewise = Baseurl + "GetMandateDateWise";
    //mandate update
    public static final String mandateupdate = Baseurl + "UploadNameAsper";
    //LogoImage
    public static final String logoimage = Baseurl + "LogoImage";
    //change password
    public static final String changepassword = Baseurl + "ChangePassword";
    //valaccountfinal
    public static final String valaccontfinal = Baseurl + "valAccountFinal";
    //EnachSucceessOrFailure
    public static final String ChkESign = Baseurl + "ChkESign";
    //GetXmlData
    public static final String GetXml = Baseurl + "GetXmlData";
    //SendOTP
    public static String SendOTP = Baseurl + "SendOTP";
    //ValidateOTP
    public static String ValidateOTP = Baseurl + "ValidateOTP";
    //ReSendOTP
    public static String ReSendOTP = Baseurl + "ReSendOTP";
    //VoiceOTP
    public static String VoiceOTP = Baseurl + "VoiceOTP";
    //SaveSignData
    public static String SaveSignData = Baseurl + "SaveSignData";
    //CancelEsign
    public static String CancelEsign = Baseurl + "CancelEsign";
    //UpdateIsPhysical
    public static String UpdateIsPhysical = Baseurl + "UpdateIsPhysical";
}
