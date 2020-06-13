package it.raffinato.dev.lensminder.ui.userinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.transition.MaterialArcMotion;
import com.google.android.material.transition.MaterialContainerTransform;

import it.raffinato.dev.lensminder.R;

public class TSUserInfoFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialContainerTransform mct = new MaterialContainerTransform();
        mct.setDuration(1000).setPathMotion(new MaterialArcMotion());
        mct.setFadeMode(MaterialContainerTransform.FADE_MODE_THROUGH);
        setSharedElementEnterTransition(mct);
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ts_user_info_fragment, container, false);
        return root;
    }


}
