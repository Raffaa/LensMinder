package it.raffinato.dev.lensminder.ui.userinfo;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.transition.Slide;

import it.raffinato.dev.lensminder.R;

public class TSUserInfoDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new TSUserInfoDialog(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.ts_user_info_fragment, container, true);
        View googleLogin = root.findViewById(R.id.googleLogin);
        final View settings = root.findViewById(R.id.settingsIcon);
        if (googleLogin != null) {
            googleLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Prova", Toast.LENGTH_LONG).show();
                }
            });
        }
        if (settings != null) {
            final NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controller.navigate(R.id.action_TSUserInfoDialogFragment_to_settingsFragment);
                }
            });
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
