package it.raffinato.dev.lensminder.utils;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

public class SharedPrefLiveData extends LiveData<Integer> {

    private String key = "lensesremaining";
    private SharedPreferences pref;

    public SharedPrefLiveData(SharedPreferences pref) {
        super();
        this.pref = pref;
        this.setValue(pref.getInt(key, -1));
    }

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (SharedPrefLiveData.this.key.equals(key)) {
                setValue(sharedPreferences.getInt(key, -1));
            }
        }
    };

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
