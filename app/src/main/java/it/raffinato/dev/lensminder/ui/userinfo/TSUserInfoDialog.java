package it.raffinato.dev.lensminder.ui.userinfo;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.techisfun.android.topsheet.TopSheetDialog;

import it.raffinato.dev.lensminder.R;

public class TSUserInfoDialog extends TopSheetDialog {

    public TSUserInfoDialog(@NonNull final Context context) {
        super(context);
        setContentView(R.layout.ts_user_info_fragment);
    }

    public TSUserInfoDialog(@NonNull Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.ts_user_info_fragment);
    }

    protected TSUserInfoDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setContentView(R.layout.ts_user_info_fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.findViewById(R.id.ddd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Prova", Toast.LENGTH_LONG).show();
            }
        });
    }
}
