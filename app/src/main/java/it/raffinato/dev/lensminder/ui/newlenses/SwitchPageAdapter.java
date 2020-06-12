package it.raffinato.dev.lensminder.ui.newlenses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SwitchPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> layouts;

    public SwitchPageAdapter(List<Integer> layouts) {
        this.layouts = layouts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return layouts.get(position);
    }

    @Override
    public int getItemCount() {
        return layouts.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {

        public SliderViewHolder(View view) {
            super(view);
        }
    }
}