package cf.qwikcheck.qwikcheck.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "QWIKCHECK_PREF_FILE";

    private final SharedPreferences mPref;
    public PreferencesHelper(Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public int getInt(String preferenceKey, int preferenceDefaultValue) {
        return mPref.getInt(preferenceKey, preferenceDefaultValue);
    }

    public void putInt(String preferenceKey, int preferenceValue) {
        mPref.edit().putInt(preferenceKey, preferenceValue).apply();
    }

    public long getLong(String preferenceKey, long preferenceDefaultValue) {
        return mPref.getLong(preferenceKey, preferenceDefaultValue);
    }

    public void putLong(String preferenceKey, long preferenceValue) {
        mPref.edit().putLong(preferenceKey, preferenceValue).apply();
    }

    public float getFloat(String preferenceKey, float preferenceDefaultValue) {
        return mPref.getFloat(preferenceKey, preferenceDefaultValue);
    }

    public void putFloat(String preferenceKey, float preferenceValue) {
        mPref.edit().putFloat(preferenceKey, preferenceValue).apply();
    }


    public boolean getBoolean(String preferenceKey, boolean preferenceDefaultValue) {
        return mPref.getBoolean(preferenceKey, preferenceDefaultValue);
    }

    public void putBoolean(String preferenceKey, boolean preferenceValue) {
        mPref.edit().putBoolean(preferenceKey, preferenceValue).apply();
    }

    public String getString(String preferenceKey, String preferenceDefaultValue) {
        return mPref.getString(preferenceKey, preferenceDefaultValue);
    }

    public void putString(String preferenceKey, String preferenceValue) {
        mPref.edit().putString(preferenceKey, preferenceValue).apply();
    }


}
