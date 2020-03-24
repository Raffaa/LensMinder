package it.raffinato.dev.lensminder.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;

import it.raffinato.dev.lensminder.R;

public abstract class BottomSheetBaseFragment extends BottomSheetDialogFragment {

    protected int layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout();
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bottom_sheet_base_fragment, container, false);
        MaterialCardView viewContainer = root.findViewById(R.id.materialCard);
        viewContainer.addView(inflater.inflate(layout, null, true));

        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d1 = (BottomSheetDialog) dialog;
                d1.findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackground(new ColorDrawable(Color.TRANSPARENT));
            }
        });
        return d;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Force Expanded State
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /**
     * Set the layout resources to be inflated.
     * Usage: this.layout = R.layout.whatever
     */
    public abstract void setLayout();
}
