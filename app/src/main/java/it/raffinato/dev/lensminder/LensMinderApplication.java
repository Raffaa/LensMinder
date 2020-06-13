package it.raffinato.dev.lensminder;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import it.raffinato.dev.lensminder.room.ApplicationDB;
import it.raffinato.dev.lensminder.ui.home.HomeFragment;

import static it.raffinato.dev.lensminder.ui.home.HomeFragment.sharedPrefKey;

public class LensMinderApplication extends Application {
    private static LensMinderApplication instance;
    private static ApplicationDB database;

    public static final String DBDateTimeFormat = "dd/MM/yyyy";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static LensMinderApplication instance() {
        return instance;
    }

    public static ApplicationDB getDB() {
        if(database == null){
            database = Room.databaseBuilder(instance, ApplicationDB.class, "lenstimerv2.db").build();
        }
        return database;
    }

    public void setStockLevel(int value) {
        SharedPreferences.Editor editor = this.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE).edit();
        editor.putInt("lensesremaining", value);
        editor.apply();
    }

    public void decreaseStockLevelByN(int N) {
        SharedPreferences sp = this.getSharedPreferences(sharedPrefKey, MODE_PRIVATE);
        int oldVal = sp.getInt("lensesremaining", -1);
        int newVal = Math.max(oldVal - N, 0);
        SharedPreferences.Editor editor = this.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE).edit();
        editor.putInt("lensesremaining", newVal);
        editor.apply();
    }
}
