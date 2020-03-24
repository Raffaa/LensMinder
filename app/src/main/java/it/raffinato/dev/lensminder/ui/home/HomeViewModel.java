package it.raffinato.dev.lensminder.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.raffinato.dev.lensminder.repository.LensesRepository;
import it.raffinato.dev.lensminder.room.LensesModel;

public class HomeViewModel extends ViewModel {

    private final LensesRepository lensesRepository;

    private final MutableLiveData<List<LensesModel>> lenses;

    public HomeViewModel() {
        this.lensesRepository = LensesRepository.getInstance();
        this.lenses = new MutableLiveData<>();
    }

    LiveData<List<LensesModel>> getActiveLenses() {
        return lensesRepository.getLenses();
    }
}
