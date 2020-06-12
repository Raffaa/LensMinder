package it.raffinato.dev.lensminder.repository;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import it.raffinato.dev.lensminder.LensMinderApplication;
import it.raffinato.dev.lensminder.room.LensesModel;
import it.raffinato.dev.lensminder.room.dao.LensesDao;
import it.raffinato.dev.lensminder.utils.AsyncListener;
import it.raffinato.dev.lensminder.utils.LensesWrapper;

public class LensesRepository {
    private static LensesRepository instance;

    private final LensesDao lensesDao;
    private final Executor executor;

    private LensesRepository() {
        lensesDao = LensMinderApplication.getDB().lensesDao();
        executor = Executors.newCachedThreadPool();
    }

    public static LensesRepository instance() {
        if (instance == null) {
            synchronized (LensesRepository.class) {
                if (instance == null)
                    instance = new LensesRepository();
            }
        }

        return instance;
    }

    public LiveData<List<LensesModel>> getLenses() {
        return lensesDao.getActiveLenses();
    }

    public void addLenses(final LensesWrapper currentActive, final LensesWrapper brandNew, final AsyncListener asyncListener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(currentActive != null) {
                    lensesDao.addNewLenses(currentActive.toModel().deactivate(), brandNew.toModel());
                } else {
                    lensesDao.insert(brandNew.toModel());
                }

                asyncListener.onDone();
            }
        });
    }
}
