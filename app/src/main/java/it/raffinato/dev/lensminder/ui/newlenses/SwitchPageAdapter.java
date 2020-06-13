package it.raffinato.dev.lensminder.ui.newlenses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.badoualy.datepicker.DatePickerTimeline;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.raffinato.dev.lensminder.R;
import it.raffinato.dev.lensminder.utils.LensesWrapper;

public class SwitchPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Integer> layouts;
    private final List<View> views;
    private final LensesWrapper lenses;

    public SwitchPageAdapter(List<Integer> layouts, LensesWrapper lenses) {
        this.views = new ArrayList<>();
        this.layouts = layouts;
        this.lenses = lenses;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        views.add(view);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (lenses != null) {
            switch (position) {
                case 0:
                    ((SliderViewHolder) holder).spinner.setSelection(lenses.getLxLensDuration().getId());
                    break;
                case 1:
                    ((SliderViewHolder) holder).spinner.setSelection(lenses.getRxLensDuration().getId());
                    break;
                default:
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return layouts.get(position);
    }

    @Override
    public int getItemCount() {
        return layouts.size();
    }

    public View getViewAt(int position) {
        return this.views.get(position);
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        final Spinner spinner;
        final DatePickerTimeline datePicker;

        public SliderViewHolder(View view) {
            super(view);
            spinner = view.findViewById(R.id.spinner);
            datePicker = view.findViewById(R.id.datepicker);
            initSpinner();
            initDatePicker();
        }

        private void initDatePicker() {
            DateTime now = DateTime.now();
            datePicker.setFirstVisibleDate(now.getYear() - 1, Calendar.JANUARY, 1);
            datePicker.setLastVisibleDate(now.getYear() + 2, Calendar.DECEMBER, 31);
            datePicker.setSelectedDate(now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());
        }

        private void initSpinner() {
            spinner.setAdapter(ArrayAdapter.createFromResource(spinner.getContext(), R.array.str_arr_spinner, R.layout.support_simple_spinner_dropdown_item));
        }
    }
}