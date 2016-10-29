package cf.qwikcheck.qwikcheck.helper;

import android.content.Context;

import cf.qwikcheck.qwikcheck.utils.Constants;

/**
 * Created by akshit on 23/10/16.
 */

public class SessionHelper {

    private PreferencesHelper mPreferencesHelper;

    public SessionHelper(Context context) {
        mPreferencesHelper = new PreferencesHelper(context);
    }

    public boolean isLoggedIn() {
        return (getUserID()!=0);
    }

    public void logout() {
        mPreferencesHelper.clear();
    }

    public int getUserID() {
        return mPreferencesHelper.getInt(Constants.USER_ID_KEY,0);
    }

    public void setUserID(int UserID) {
        mPreferencesHelper.putInt(Constants.USER_ID_KEY,UserID);
    }

    public String getUsername() {
        return mPreferencesHelper.getString(Constants.USER_NAME_KEY,"");
    }

    public void setUsername(String Username) {
        mPreferencesHelper.putString(Constants.USER_NAME_KEY,Username);
    }

    public String getRealname() {
        return mPreferencesHelper.getString(Constants.REAL_NAME_KEY,"");
    }

    public void setRealname(String Realname) {
        mPreferencesHelper.putString(Constants.REAL_NAME_KEY,Realname);
    }

    public String getUsertype() {
        return mPreferencesHelper.getString(Constants.USER_TYPE_KEY,"");
    }

    public void setUsertype(String usertype) {
        mPreferencesHelper.putString(Constants.USER_TYPE_KEY,usertype);
    }

    public boolean isProfileComplete() {
        return mPreferencesHelper.getBoolean(Constants.PROFILE_COMPLETE_KEY,false);
    }

    public void setProfileComplete(boolean profileComplete) {
        mPreferencesHelper.putBoolean(Constants.PROFILE_COMPLETE_KEY,profileComplete);
    }

}
