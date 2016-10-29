package cf.qwikcheck.qwikcheck.utils;

/**
 * Created by akshit on 23/10/16.
 */

public class Constants {

    public static final String API_BASE_URL = "https://qwikcheck.cf/";

    public static final String USER_LOGIN_URL = API_BASE_URL + "users/login.php";
    public static final String USER_REG_URL = API_BASE_URL + "users/register.php";

    public static final String CHECK_URL = API_BASE_URL + "certificates.php";

    // Preferences keys
    public static final String PREF_FILE_NAME = "QWIKCHECK_PREF_FILE";

    public static final String USER_ID_KEY = "userID";
    public static final String USER_NAME_KEY = "userName";
    public static final String REAL_NAME_KEY = "realName";
    public static final String USER_TYPE_KEY = "userType";
    public static final String PROFILE_COMPLETE_KEY = "profileComplete";
 }