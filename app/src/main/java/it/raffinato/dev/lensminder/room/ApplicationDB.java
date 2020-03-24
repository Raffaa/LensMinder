package it.raffinato.dev.lensminder.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import it.raffinato.dev.lensminder.room.dao.LensesDao;

@Database(entities = {LensesModel.class}, version = 1, exportSchema = false)
public abstract class ApplicationDB extends RoomDatabase {
        public abstract LensesDao lensesDao();
}
