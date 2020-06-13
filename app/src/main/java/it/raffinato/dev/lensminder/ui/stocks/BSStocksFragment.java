package it.raffinato.dev.lensminder.ui.stocks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import it.raffinato.dev.lensminder.LensMinderApplication;
import it.raffinato.dev.lensminder.R;
import it.raffinato.dev.lensminder.ui.BottomSheetBaseFragment;

public class BSStocksFragment extends BottomSheetBaseFragment {

    private Integer stockNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        if (b != null) {
            stockNumber = BSStocksFragmentArgs.fromBundle(getArguments()).getStockNumber();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initTextArea(view);
        initButtons(view);
    }

    @Override
    public void setLayout() {
        this.layout = R.layout.bs_stocks_fragment;
    }

    private void initTextArea(View view) {
        TextInputEditText textInputEditText = view.findViewById(R.id.case_lenses_remaining);
        textInputEditText.setText(stockNumber.toString());
    }

    private void initButtons(final View view) {
        Button bs = view.findViewById(R.id.case_save);
        Button br = view.findViewById(R.id.case_reset);
        final TextInputEditText et = view.findViewById(R.id.case_lenses_remaining);
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LensMinderApplication.instance().setStockLevel(Integer.parseInt(et.getText().toString()));
                dismiss();
            }
        });
        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextInputEditText) view.findViewById(R.id.case_lenses_remaining)).setText("0");
            }
        });
    }
}
