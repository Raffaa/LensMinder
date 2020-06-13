package it.raffinato.dev.lensminder.utils;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

public class SharedPrefLiveData extends MutableLiveData<Integer> {

    private final String key = "lensesremaining";
    private final SharedPreferences pref;
    private final SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (SharedPrefLiveData.this.key.equals(key)) {
                setValue(sharedPreferences.getInt(key, -1));
            }
        }
    };

    public SharedPrefLiveData(SharedPreferences pref) {
        super();
        this.pref = pref;
        this.postValue(pref.getInt(key, -1));
    }

    @Override
    protected void onActive() {
        super.onActive();
        pref.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        pref.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }
}
