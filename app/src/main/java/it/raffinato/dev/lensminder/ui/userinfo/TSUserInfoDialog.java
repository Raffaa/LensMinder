package it.raffinato.dev.lensminder.ui.userinfo;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import com.github.techisfun.android.topsheet.TopSheetDialog;

import it.raffinato.dev.lensminder.R;

public class TSUserInfoDialog extends TopSheetDialog {

    public TSUserInfoDialog(@NonNull final Context context) {
        super(context);
        //setContentView(R.layout.ts_user_info_fragment);
    }

    public TSUserInfoDialog(@NonNull Context context, int theme) {
        super(context, theme);
        //setContentView(R.layout.ts_user_info_fragment);
    }

    protected TSUserInfoDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        //setContentView(R.layout.ts_user_info_fragment);
    }

    public TSUserInfoDialog(Context context, @NonNull FragmentActivity activity) {
        super(context);
        setOwnerActivity(activity);
        setContentView(R.layout.ts_user_info_fragment);
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        View googleLogin = findViewById(R.id.googleLogin);
        View settings = findViewById(R.id.settingsIcon);
        if(googleLogin != null) {
            googleLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Prova", Toast.LENGTH_LONG).show();
                }
            });
        }
        if (settings != null) {
            final NavController controller = Navigation.findNavController(getOwnerActivity(), R.id.nav_host_fragment);
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controller.navigate(R.id.action_homeFragment_to_settingsFragment);
                    dismiss();
                }
            });
        }
    }

     */
}
