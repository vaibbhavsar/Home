package poi.ivyphlox.com.poivender.network;


import poi.ivyphlox.com.poivender.utils.AppConstants;

/**
 * Created by admin on 10/21/2016.
 */

public class NCLUrls implements AppConstants {
    //Production Environment
//    static String BASE_URL = "http://ec2-18-221-160-33.us-east-2.compute.amazonaws.com:8080/projectx/api/";
    public static String BASE_URL = "https://j7jjqlqd6l.execute-api.ap-south-1.amazonaws.com/dev/";

    public static String HELPLINE_CONTACTS_URL= BASE_URL+"helplineJson.php";
    public static String LOGIN_URL= BASE_URL+"authenticate";
    public static String REGISTRATION_URL= BASE_URL+"registration";
    public static String SEND_NOTIFICATION_URL= BASE_URL+"notification";
    public static String RESET_PASSWORD= BASE_URL+"password";
    public static String UPDATE_PROFILE= BASE_URL+"profile";
    public static String GET_PROFILE= BASE_URL+"profile/";
    public static String GET_MEMBERS= BASE_URL+"member-status";
    public static String GET_NOTIFICATION= BASE_URL+"notification/";


}
