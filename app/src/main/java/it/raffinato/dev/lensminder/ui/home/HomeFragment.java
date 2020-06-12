package it.raffinato.dev.lensminder.ui.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.gelitenight.waveview.library.WaveView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.joda.time.format.DateTimeFormat;

import java.util.List;
import java.util.Locale;

import it.raffinato.dev.lensminder.R;
import it.raffinato.dev.lensminder.room.LensesModel;
import it.raffinato.dev.lensminder.utils.Lens;
import it.raffinato.dev.lensminder.utils.LensesWrapper;
import it.raffinato.dev.lensminder.utils.WaveHelper;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private WaveHelper rxWaveHelper;
    private WaveHelper lxWaveHelper;

    private HomeViewModel mViewModel;

    private int stockNumber = 0;

    private LensesWrapper activeLenses;

    public static final String sharedPrefKey = "splensesincase";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        Activity a = getActivity();
        if(a != null) {
            SharedPreferences pref = getActivity().getSharedPreferences(sharedPrefKey, MODE_PRIVATE);
            mViewModel.setSharedPrefLiveData(pref);
        } else {
            Log.e(getClass().getName(), "Activity NULL;");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        final WaveView lxWaveView = root.findViewById(R.id.wave_lx);
        final WaveView rxWaveView = root.findViewById(R.id.wave_rx);
        final GridLayout gridLayout = root.findViewById(R.id.gridlayout);
        final MaterialButton addNewLensesButton = root.findViewById(R.id.addNewLensesButton);
        initWaveView(lxWaveView);
        initWaveView(rxWaveView);
        initGridLayout(gridLayout);
        initAddNewLensesButton(addNewLensesButton);

        rxWaveHelper = new WaveHelper(rxWaveView, 0.0f);
        lxWaveHelper = new WaveHelper(lxWaveView, 0.0f);

        return root;
    }

    private void initAddNewLensesButton(MaterialButton addNewLensesButton) {
        addNewLensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToBSNewLensesFragment().setModel(activeLenses));
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.getActiveLenses().observe(getViewLifecycleOwner(), new Observer<List<LensesModel>>() {
            @Override
            public void onChanged(List<LensesModel> lensList) {
               if(!lensList.isEmpty()) {
                   LensesModel lenses = lensList.get(0);
                   Lens lxLens = new Lens(lenses.getInitDateLx(), lenses.getDurationLx(), lenses.getActiveLx());
                   Lens rxLens = new Lens(lenses.getInitDateRx(), lenses.getDurationRx(), lenses.getActiveRx());
                   activeLenses = new LensesWrapper(lxLens, rxLens, lenses.getId());
                   initLensesArea(view);
                   if(lensList.size() > 1) {
                       lenses = lensList.get(0);
                       lxLens = new Lens(lenses.getInitDateLx(), lenses.getDurationLx(), lenses.getActiveLx());
                       rxLens = new Lens(lenses.getInitDateRx(), lenses.getDurationRx(), lenses.getActiveRx());
                       initHistoryPanel(view, new LensesWrapper(lxLens, rxLens, lenses.getId()));
                   }
               }
            }
        });

        mViewModel.getSharedPrefLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {
                editStocksView(view, value);
                stockNumber = value;
            }
        });

    }

    private void editStocksView(View view, Integer value) {
        FrameLayout container = view.findViewById(R.id.frameLayoutStockNumber);
        AppCompatImageView icon = view.findViewById(R.id.stocksIcon);
        MaterialTextView materialTextView = view.findViewById(R.id.stockNumber);

        if(value >= 0) {
            icon.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
            materialTextView.setText(String.format(Locale.getDefault(), "%d", value));
        } else {
            icon.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        lxWaveHelper.cancel();
        rxWaveHelper.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        lxWaveHelper.start();
        rxWaveHelper.start();
    }

    private void initGridLayout(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View v = gridLayout.getChildAt(i);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.shop:
                            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_storeFragment);
                            Log.d("YYY", "SHOP");
                            break;
                        case R.id.history:
                            Log.d("YYY", "HISTORY");
                            break;
                        case R.id.stocks:
                            Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToBSStocksFragment(stockNumber));
                            Log.d("YYY", "STOCKS");
                            break;
                        case R.id.settings:
                            Log.d("YYY", "SETTINGS");
                            break;

                        default:
                            throw new IllegalStateException("Unexpected value: " + v.getId());
                    }
                }
            });
        }
    }

    private void initWaveView(WaveView waveView) {
        waveView.setShapeType(WaveView.ShapeType.SQUARE);
        waveView.setBorder(0, 0);
        waveView.setWaveColor(Color.parseColor("#285364e0"), Color.parseColor("#3C8b91ff"));
    }

    private void initHistoryPanel(View view, LensesWrapper lenses) {

    }

    private void initLensesArea(View view) {
        if(activeLenses.hasLensesActive()) {
            AppCompatTextView lxCountdown = view.findViewById(R.id.lx_countdown);
            AppCompatTextView lxExpDate = view.findViewById(R.id.lx_eta);
            AppCompatTextView rxCountdown = view.findViewById(R.id.rx_countdown);
            AppCompatTextView rxExpDate = view.findViewById(R.id.rx_eta);

            lxCountdown.setText(activeLenses.getLxLensRemainingTime().toString());
            lxExpDate.setText(activeLenses.getLxLensExpDate().toString(DateTimeFormat.shortDate()));
            lxWaveHelper.changeLevel(activeLenses.getLxLensRemainingTime() / (float) activeLenses.getLxLensDuration().getTime());
            rxCountdown.setText(activeLenses.getRxLensRemainingTime().toString());
            rxExpDate.setText(activeLenses.getRxLensExpDate().toString(DateTimeFormat.shortDate()));
            rxWaveHelper.changeLevel(activeLenses.getRxLensRemainingTime() / (float) activeLenses.getRxLensDuration().getTime());
        }
    }

}
