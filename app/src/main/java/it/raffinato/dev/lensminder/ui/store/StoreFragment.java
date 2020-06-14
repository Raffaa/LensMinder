package it.raffinato.dev.lensminder.ui.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.transition.MaterialArcMotion;
import com.google.android.material.transition.MaterialContainerTransform;

import it.raffinato.dev.lensminder.R;

public class StoreFragment extends Fragment {

    private StoreViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(StoreViewModel.class);

        MaterialContainerTransform mct = new MaterialContainerTransform();
        mct.setDuration(600);
        mct.setFadeMode(MaterialContainerTransform.FADE_MODE_THROUGH);
        setSharedElementEnterTransition(mct);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.store_fragment, container, false);
    }

}
