package poi.ivyphlox.com.poivender.utils;

/**
 * Created by Morya on 3/20/2018.
 */

public interface AppConstants {

    String PIPE_VALUES_SEPERATOR = "\\|";
    String DOT_VALUES_SEPERATOR = "\\.";
    String APP_CLIENT_TYPE = "Vendor";
    String APP_NAME = "WineList";
    String APP_CLIENT_TYPE_KEY = "usertype";
    String APP_AUTHORIZATION="Authorization";
    String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    int MINIMUM_PASSWORD = 4;
    int MAXIMUM_PASSWORD = 15;
    int MINIMUM_MOBILE = 10;
    int MAXIMUM_MOBILE = 10;

    int STATUS_SUCCESS = 200, STATUS_AUTH_ERROR = 401, STATUS_FORBIDDEN = 403, STATUS_NOT_FOUND = 404, STATUS_INT_SERVER_ERROR = 500;

// ***********Shared Preferences*****************
    public int MAXIMUM_OFFERS = 5;
    int MAXIMUM_RECENT = 10;
    int MAXIMUM_SPONSERED = 10;

    //    *****Boolean******
    String PREFS_IS_LOGIN = "isLoggedIn";

    //******ResultKeys******
    int RESULT_TRUE = 1;
    int RESULT_FALSE = 0;

    //*****String*****

    String PREFS_AGE_LIMIT = "ageLimit", PREFS_GEO_SEARCH_DISTANCE = "geoSearchDistance", PREFS_APP_DATE_FORMAT = "appDateFormat";
    String PREFS_LATITUDE = "latitude", PREFS_LONGITUDE = "longitude";
    String PREFS_LOGGED_IN_USER = "loggedInUser";
    String PREFS_USER = "inUser";
    String PREFS_CURRENT_TANDC_DATA = "currentTandCData";
    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    String AUTH_TOKEN = "authToken";
    String INDIAN_RUPEE_SIGN = "₹ ";
    String NO_OFFERS_TO_DISPLAY="NO_OFFERS_TO_DISPLAY";
    String NO_RECENTLY_TO_DISPLAY="NO_RECENTLY_TO_DISPLAY";
    String NO_NEARBY_TO_DISPLAY="NO_NEARBY_TO_DISPLAY";
    String NO_SPONSERED_TO_DISPLAY="NO_SPONSERED_TO_DISPLAY";
    String NO_BOOKMARKED_TO_DISPLAY="NO_BOOKMARKED_TO_DISPLAY";
    //    ******Intent key******
    String KEY_ESTABLISHMENT = "establishment";
    String KEY_SELECTED_INDEX = "selectedIndex";
    String KEY_SOCIAL="social";
    String KEY_OFFER_LIST = "offersList";
    String KEY_OFFER_ITEM = "offersItem";
    String KEY_REVIEW_LIST = "reviewList";
    String KEY_ESTABLISHMENT_LIST = "establishmentList";
    String KEY_MONDAY = "MO";
    String KEY_TUESDAY = "TU";
    String KEY_WEDNESDAY = "WE";
    String KEY_THURSDAY = "TH";
    String KEY_FRIDAY = "FR";
    String KEY_SATURDAY = "SA";
    String KEY_SUNDAY = "SU";
    String KEY_SELECTION = "selection";
    String IS_FROM_REGISTER="is_from_registration";
    String MOBILE_NO="mobile_no";
    //    *******User*******
    String KEY_USER_MSG="msg";
    String KEY_USER_WHTSAPP="whatsapp";
    String KEY_USER_FACEBOOK="facebook";
    String KEY_USER_TWITTER="twitter";
    String KEY_USER_WE_CHAT="wechat";
    String KEY_USER_IMAGES= "images";
    String KEY_USER_IMAGES_OBJECTTYPE= "objectType";
    String KEY_USER_UUID = "uuid";
    String KEY_USER_DISPLAY_NAME = "displayName";
    String KEY_USER_FIRST_NAME = "fName";
    String KEY_USER_LAST_NAME = "lName";
    String KEY_USER_EMAIL = "email";
    String KEY_USER_MOBILE = "mobile";
    String KEY_USER_PASSWORD = "password";
    String KEY_USER_DOB = "dob";
    String KEY_USER_GENDER_CODE = "genderCode";
    String KEY_USER_USER_ROLE_CODE = "userRoleCode";
    String KEY_USER_PROFILE_PHOTO_URL = "profilePhotoURL";
    String KEY_USER_IS_ACTIVE = "isActive";
    String KEY_USER_IS_LOGEDIN = "isLogedin";
    String KEY_USER_IS_EMAIL_VERIFIED = "isEmailVerified";
    String KEY_USER_IS_MOBILE_VERIFIED = "isMobileVerified";
    String KEY_USER_IS_TANDC_AGREE = "isTandCAgree";
    String KEY_USER_IS_PASSWORD_CHANGED = "isPasswordChanged";
    String KEY_USER_TANDC_ID = "tandcId";
    String KEY_USER_AREA_ID = "areaId";
    String KEY_USER_AREA_NAME = "areaName";
    String KEY_USER_CITY_ID = "cityId";
    String KEY_USER_CITY_CODE = "cityCode";
    String KEY_USER_CITY_NAME = "cityName";
    String KEY_USER_STATE_ID = "stateId";
    String KEY_USER_STATE_CODE = "stateCode";
    String KEY_USER_STATE_NAME = "stateName";
    String KEY_USER_COUNTRY_ID = "countryId";
    String KEY_USER_COUNTRY_CODE = "countryCode";
    String KEY_USER_COUNTRY_NAME = "countryName";
    String KEY_USER_ZIP_ID = "zipId";
    String KEY_USER_ZIP_CODE = "zipCode";
    String KEY_USER_ZIP_NAME = "zipName";
    String KEY_USER_CREATED_DATE = "createdDate";
    String KEY_USER_MODIFIED_DATE = "modifiedDate";
    String KEY_USER_AGREE_TANDC_DATE = "agreeTandCDate";
    String KEY_USER_OTP = "otp";
    String KEY_USER_IS_DELETED = "isDeleted";
    String KEY_SELECTED_LOCATION = "selectedLocation";
    String KEY_OBJECTTYPE="objectType";
    String KEY_OBJECT_TYPE_USSR="object_type.User";
    String KEY_OBJECT_TYPE_CONSUMMER="object_type.consumer";
    String KEY_OBJECT_TYPE_ESTABLISHMENT="object_type.Establishment";
    String KEY_RESOURCE_ID="resourceId";
    String KEY_IS_DEFOULT_RESOURCE="isDefaultResource";
    String KEY_USERNAME="username";
    String KEY_PASSWORD="password";
    String KEY_CONFIG_CODE="configCode";
    String KEY_VERIFICATION_OTP="verifyOtp";
    String KEY_REVERIFICATION_EMAIL="resendVerificationEmail";
    String KEY_REVERIFICATION_OTP="resendVerificationEmail";
    String KEY_COUPONS="coupons";


    //    *************
    String MLOV_GENDER_TYPE = "gender_type";
    String MLOV_ESTABLISHMENT_TYPE = "establishment_type";
    String MLOV_USER_TYPE = "user_type";
    String MLOV_LIQUOR_TYPE = "liquor_type";
    String MLOV_RESOURCE_AVAIALIBILITY_TYPE = "resource_avaialibility_type";
    String MLOV_OFFER_TYPE = "offer_type";
    String MLOV_DISCOUNT_TYPE = "discount_type";
    String MLOV_PAYMENT_MEHTOD_TYPE = "payment_mehtod_type";


    //************************DATABASE
    String DATABASE_NAME = "wineList.db";
    //    MLOV table
    String TABLE_NAME_MLOVS = "mlovs";
    public static String TABLE_NAME_CONFIG = "config";
    //    "mlovCodePK": "gender_type.F",
//            "mlovCode": "F",
//            "mlovTypeCode": "gender_type",
//            "mlovDescription": "Female",
//            "isDeleted": false
    String MLOV_CODE_PK = "mlovCodePK";
    String MLOV_CODE = "mlovCode";
    String MLOV_TYPE_CODE = "mlovTypeCode";
    String MLOV_DESCRIPTION = "mlovDescription";
    String MLOV_IS_DELETED = "isDeleted";


    //config table
    String CONFIG_CONFIGID="configId";
    String CONFIG_CONFIGCODE="configCode";
    String CONFIG_CONFIG_DESCRIBE="configDescription";
    String CONFIG_CONFIGVALUE="configValue";
    String CONFIG_CONFIGBY="createdBy";
    String CONFIG_CONFIGDATETIME="createdDatetime";
    String CONFIG_CONFIGMODIFIEDBY="modifiedBy";
    String CONFIG_CONFIGMODIFIEDDATETIME="modifiedDatetime";
    String CONFIG_CONFIGISDELETE="isDeleted";

    //    City Table
    String TABLE_NAME_CITIES = "cities";
    String KEY_CITIES_CITYCODE = "cityCode";
    String KEY_CITIES_CITYNAME = "cityName";
    String KEY_CITIES_STATECODE = "stateCode";
    String KEY_CITIES_COUNTRYCODE = "countryCode";
    String KEY_CITIES_LAT = "lat";
    String KEY_CITIES_LON = "lon";
    String KEY_CITIES_IS_LAUNCHED = "isLaunched";
    String KEY_CITIES_LAUNCHDATE = "launchDate";
    String KEY_CITIES_IS_DELETED = "isDeleted";
//    "cityCode": "Pune",//            "cityName": "Pune",//            "stateCode": "MH",//            "countryCode": "IND",//            "lat": 18.5204,//            "lon": 73.8567,//            "isLaunched": true,//            "launchDate": "2017-01-01T00:00:00.000+0000",//            "isDeleted": false

    String TABLE_NAME_RESOURCE = "resources";
    String KEY_RESOURCE_OBJECTCATTYPE = "objectCatType";
    String KEY_RESOURCE_ID1 = "resourceId";
    String KEY_RESOURCE_OBJECTTYPE = "objectType";
    String KEY_RESOURCE_CMSID = "cmsId";
    String KEY_RESOURCE_BRANDNAME = "brandName";
    String KEY_RESOURCE_BRANDNAME_ARRAYLIST = "brandNameArray";

    String KEY_RESOURCE_MANUFACTURER_NAME = "manufacturerName";
    String KEY_RESOURCE_MANUFACTURER_COUNTRY = "manufacturerCountry";
    String KEY_RESOURCE_CAPACITY = "resourceCapacity";
    String KEY_RESOURCE_NAME = "resourceName";
    String KEY_RESOURCE_STRENGTH = "resourceStrength";
    String KEY_RESOURCE_SECONDAR_YLEGEND = "secondaryLegend";
    String KEY_RESOURCE_IS_DELETED = "isDeleted";

    String KEY_BRANDS = "Brands";
    String KEY_CATEGORIES = "Categories";

    String KEY_DISCOUNT_TYPE_A="discount_type.A";
    String KEY_DISCOUNT_TYPE_P="discount_type.P";
    String KEY_DISCOUNT_TYPE_BG="discount_type.BG";

    String KEY_OFFER_TYPE_F="offer_type.F";



    String OTP_LENGTH="OTP_LENGTH";
    String USER_TYPE_CMS="?objectType=object_type.User";
    String PREFS_MOBILE = "pref_mobile";
//    "resourceId": 1,            "objectType": "object_type.Liquor",            "objectCatType": "liquor_type.WN",            "cmsId": "1.jpg",            "brandName": "Errazuriz Max Reserva Chardonnay",            "manufacturerName": "Valle de Casablanca, Product of Chile",            "manufacturerCountry": "Chile",            "resourceCapacity": "750",            "resourceStrength": "40.00%",            "secondaryLegend": "",            "isDeleted": true
}
