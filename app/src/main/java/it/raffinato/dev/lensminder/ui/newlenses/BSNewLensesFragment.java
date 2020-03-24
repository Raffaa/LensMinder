package it.raffinato.dev.lensminder.ui.newlenses;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.button.MaterialButton;

import org.joda.time.DateTime;

import java.util.Calendar;

import it.raffinato.dev.lensminder.R;
import it.raffinato.dev.lensminder.repository.LensesRepository;
import it.raffinato.dev.lensminder.ui.BottomSheetBaseFragment;
import it.raffinato.dev.lensminder.utils.Lens;
import it.raffinato.dev.lensminder.utils.LensesWrapper;
import it.raffinato.dev.lensminder.utils.enums.Duration;

public class BSNewLensesFragment extends BottomSheetBaseFragment {

    private LensesWrapper lenses;
    private SpinKitView progressBar;

    private LensesRepository lensRepo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        if(b != null) {
            lenses = BSNewLensesFragmentArgs.fromBundle(getArguments()).getModel();
            lensRepo = LensesRepository.getInstance();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSpinners(view);
        initDatePicker(view);
        initSaveButton(view);
    }

    @Override
    public void setLayout() {
        this.layout = R.layout.bs_new_lenses_fragment;
    }

    private void initSpinners(View root) {
        Spinner spinner = root.findViewById(R.id.spinner);
        spinner.setAdapter(ArrayAdapter.createFromResource(root.getContext(), R.array.str_arr_spinner, R.layout.support_simple_spinner_dropdown_item));
        if(lenses != null) {
            spinner.setSelection(lenses.getLxLensDuration().getId());
        }
    }

    private void initDatePicker(View view) {
        DatePickerTimeline datePickerTimeline = view.findViewById(R.id.datepicker);
        DateTime now = DateTime.now();
        datePickerTimeline.setFirstVisibleDate(now.getYear() - 1, Calendar.JANUARY, 1);
        datePickerTimeline.setLastVisibleDate(now.getYear() + 2, Calendar.DECEMBER, 31);
        datePickerTimeline.setSelectedDate(now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());
    }

    private void initSaveButton(final View view) {
        initProgressBar(view);
        MaterialButton saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                v.setVisibility(View.INVISIBLE);
                lensRepo.addLenses(lenses, getNewLenses(view));
                dismiss();
            }
        });
    }

    private void initProgressBar(View view) {
        this.progressBar = view.findViewById(R.id.progressBar);
    }

    private void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    private LensesWrapper getNewLenses(View view) {
        DatePickerTimeline datePickerTimeline = view.findViewById(R.id.datepicker);
        AppCompatSpinner spinner = view.findViewById(R.id.spinner);

        DateTime date = new DateTime().withDate(datePickerTimeline.getSelectedYear(), datePickerTimeline.getSelectedMonth() + 1, datePickerTimeline.getSelectedDay());
        Lens lxLens = new Lens(date, Duration.fromSpinnerSelection(spinner.getSelectedItemPosition()));
        return new LensesWrapper(lxLens, lxLens);
    }
}