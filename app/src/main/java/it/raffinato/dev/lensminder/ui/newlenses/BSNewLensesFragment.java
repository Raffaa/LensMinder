package it.raffinato.dev.lensminder.ui.newlenses;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.viewpager2.widget.ViewPager2;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.button.MaterialButton;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;

import it.raffinato.dev.lensminder.LensMinderApplication;
import it.raffinato.dev.lensminder.R;
import it.raffinato.dev.lensminder.repository.LensesRepository;
import it.raffinato.dev.lensminder.ui.BottomSheetBaseFragment;
import it.raffinato.dev.lensminder.utils.AsyncListener;
import it.raffinato.dev.lensminder.utils.Lens;
import it.raffinato.dev.lensminder.utils.LensesWrapper;
import it.raffinato.dev.lensminder.utils.enums.Duration;
import it.raffinato.dev.lensminder.utils.notification.NotificationScheduler;
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
            lensRepo = LensesRepository.instance();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        initViewPager(viewPager);
        initSwitch(view, viewPager);
        initSaveButton(view, viewPager);
    }

    private void initViewPager(ViewPager2 viewPager) {
        SwitchPageAdapter adapter = new SwitchPageAdapter(new ArrayList<>(Arrays.asList(R.layout.new_lens_area, R.layout.new_lens_area)), lenses);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        viewPager.setOffscreenPageLimit(1);
    }

    @Override
    public void setLayout() {
        this.layout = R.layout.bs_new_lenses_fragment;
    }

    private void initSwitch(View view, ViewPager2 viewPager) {
        LensSwitch lensSwitch = view.findViewById(R.id.lensSwitch);

        lensSwitch.setViewPager(viewPager);
        if (lenses != null) {
            //lensSwitch.setEqualSelected(lenses.areEqual());
        }
    }

    private void initSaveButton(final View view, final ViewPager2 viewPager) {
        initProgressBar(view);
        MaterialButton saveButton = view.findViewById(R.id.saveButton);
        final LensSwitch lensSwitch = view.findViewById(R.id.lensSwitch);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                v.setVisibility(View.INVISIBLE);
                final LensesWrapper newLenses = getNewLenses(viewPager, lensSwitch.isEqualSelected());
                //TODO: trovare un modo migliore. SharedViewModel?
                AsyncListener listener = new AsyncListener() {
                    @Override
                    public void onDone() {
                        NotificationScheduler.initNotifications(newLenses);
                        LensMinderApplication.instance().decreaseStockLevelByN(2);
                        dismiss();
                    }
                };
                lensRepo.addLenses(lenses, newLenses, listener);

            }
        });
    }

    private void initProgressBar(View view) {
        this.progressBar = view.findViewById(R.id.progressBar);
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

    private LensesWrapper getNewLenses(ViewPager2 viewpager, boolean isEqualSelected) {
        View leftView = ((SwitchPageAdapter) viewpager.getAdapter()).getViewAt(0);
        View rightView = ((SwitchPageAdapter) viewpager.getAdapter()).getViewAt(1);
        if (isEqualSelected) {
            return new LensesWrapper(getLens(leftView));
        } else {
            return new LensesWrapper(getLens(leftView), getLens(rightView));
        }
    }
}