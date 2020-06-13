package it.raffinato.dev.lensminder.ui.home;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.raffinato.dev.lensminder.repository.LensesRepository;
import it.raffinato.dev.lensminder.room.LensesModel;
import it.raffinato.dev.lensminder.utils.SharedPrefLiveData;

public class HomeViewModel extends ViewModel {

    private final LensesRepository lensesRepository;

    private final MutableLiveData<List<LensesModel>> lenses;
    private SharedPrefLiveData sharedPrefLiveData;

    public HomeViewModel() {
        this.lensesRepository = LensesRepository.instance();
        this.lenses = new MutableLiveData<>();
    }

    LiveData<List<LensesModel>> getActiveLenses() {
        return lensesRepository.getLastLenses();
    }

    SharedPrefLiveData getSharedPrefLiveData() {
        return sharedPrefLiveData;
    }

    void setSharedPrefLiveData(SharedPreferences sharedPref) {
        this.sharedPrefLiveData = new SharedPrefLiveData(sharedPref);
    }
}
