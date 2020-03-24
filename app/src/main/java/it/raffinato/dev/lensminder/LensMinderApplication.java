package it.raffinato.dev.lensminder;

import android.app.Application;

import androidx.room.Room;

import it.raffinato.dev.lensminder.room.ApplicationDB;

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
}
