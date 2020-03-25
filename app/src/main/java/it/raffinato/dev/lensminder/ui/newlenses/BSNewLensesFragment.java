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
import it.raffinato.dev.lensminder.utils.view.LensSwitch;

public class BSNewLensesFragment extends BottomSheetBaseFragment {

    private LensesWrapper lenses;
    private SpinKitView progressBar;

    private LensesRepository lensRepo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        if (b != null) {
            lenses = BSNewLensesFragmentArgs.fromBundle(getArguments()).getModel();
            lensRepo = LensesRepository.getInstance();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View leftView = view.findViewById(R.id.leftLens);
        View rightView = view.findViewById(R.id.rightLens);

        initSwitch(view, leftView, rightView);
        initSpinners(leftView, rightView);
        initDatePicker(leftView, rightView);
        initSaveButton(view, leftView, rightView);
    }

    @Override
    public void setLayout() {
        this.layout = R.layout.bs_new_lenses_fragment;
    }

    private void initSwitch(View view, View left, View right) {
        LensSwitch lensSwitch = view.findViewById(R.id.lensSwitch);
        lensSwitch.setViews(left, right);
        /* TODO: capire se è utile settare il flag
        if(lenses != null) {
            lensSwitch.setEqualSelected(lenses.areEqual());
        }
         */
    }

    private void initSpinners(View leftView, View rightView) {
        Spinner leftSpinner = leftView.findViewById(R.id.spinner);
        Spinner rightSpinner = rightView.findViewById(R.id.spinner);
        setSpinnerAdapter(leftSpinner);
        setSpinnerAdapter(rightSpinner);

    }

    private void initDatePicker(View leftView, View rightView) {
        DatePickerTimeline leftDatepicker = leftView.findViewById(R.id.datepicker);
        DatePickerTimeline rightDatepicker = rightView.findViewById(R.id.datepicker);
        setDatepickerRange(leftDatepicker);
        setDatepickerRange(rightDatepicker);
    }

    private void initSaveButton(final View view, final View leftView, final View rightView) {
        initProgressBar(view);
        MaterialButton saveButton = view.findViewById(R.id.saveButton);
        final LensSwitch lensSwitch = view.findViewById(R.id.lensSwitch);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                v.setVisibility(View.INVISIBLE);
                if(lensSwitch.isEqualSelected()) {
                    lensRepo.addLenses(lenses, getNewLenses(leftView));
                } else {
                    lensRepo.addLenses(lenses, getNewLenses(leftView, rightView));
                }

                dismiss();
            }
        });
    }

    private void initProgressBar(View view) {
        this.progressBar = view.findViewById(R.id.progressBar);
    }

    private void setSpinnerAdapter(Spinner spinner) {
        spinner.setAdapter(ArrayAdapter.createFromResource(spinner.getContext(), R.array.str_arr_spinner, R.layout.support_simple_spinner_dropdown_item));
        if (lenses != null) {
            spinner.setSelection(lenses.getLxLensDuration().getId());
        }
    }

    private void setDatepickerRange(DatePickerTimeline datePickerTimeline) {
        DateTime now = DateTime.now();
        datePickerTimeline.setFirstVisibleDate(now.getYear() - 1, Calendar.JANUARY, 1);
        datePickerTimeline.setLastVisibleDate(now.getYear() + 2, Calendar.DECEMBER, 31);
        datePickerTimeline.setSelectedDate(now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());
    }

    private void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    private Lens getLens(View view) {
        DatePickerTimeline datePickerTimeline = view.findViewById(R.id.datepicker);
        AppCompatSpinner spinner = view.findViewById(R.id.spinner);
        DateTime date = new DateTime().withDate(datePickerTimeline.getSelectedYear(), datePickerTimeline.getSelectedMonth() + 1, datePickerTimeline.getSelectedDay());
        return new Lens(date, Duration.fromSpinnerSelection(spinner.getSelectedItemPosition()));
    }

    private LensesWrapper getNewLenses(View view) {
        Lens lens = getLens(view);
        return new LensesWrapper(lens);
    }

    private LensesWrapper getNewLenses(View leftView, View rightView) {
        return new LensesWrapper(getLens(leftView), getLens(rightView));
    }
}