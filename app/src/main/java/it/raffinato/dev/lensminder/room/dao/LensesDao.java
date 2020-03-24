package it.raffinato.dev.lensminder.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import it.raffinato.dev.lensminder.room.LensesModel;

@Dao
public abstract class LensesDao {

    @Query("SELECT * FROM lenses ORDER BY _id DESC LIMIT 2")
    public abstract LiveData<List<LensesModel>> getActiveLenses();

    @Insert()
    public abstract void insert(LensesModel l);

    @Update
    public abstract void deactivateCurrentLenses(LensesModel model);

    @Transaction
    public void addNewLenses(LensesModel old, LensesModel nevv) {
        deactivateCurrentLenses(old);
        insert(nevv);
    }


}
