package it.raffinato.dev.lensminder.ui.home;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.transition.Fade;

import com.gelitenight.waveview.library.WaveView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.transition.MaterialContainerTransform;

import org.joda.time.format.DateTimeFormat;

import java.util.List;
import java.util.Locale;

import it.raffinato.dev.lensminder.R;
import it.raffinato.dev.lensminder.room.LensesModel;
import it.raffinato.dev.lensminder.utils.Lens;
import it.raffinato.dev.lensminder.utils.LensesWrapper;
import it.raffinato.dev.lensminder.utils.WaveHelper;
import it.raffinato.dev.lensminder.utils.enums.Duration;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    public static final String sharedPrefKey = "splensesincase";
    private WaveHelper rxWaveHelper;
    private WaveHelper lxWaveHelper;
    private HomeViewModel mViewModel;
    private int stockNumber = 0;
    private LensesWrapper activeLenses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        SharedPreferences pref = requireActivity().getSharedPreferences(sharedPrefKey, MODE_PRIVATE);
        mViewModel.setSharedPrefLiveData(pref);

        setExitTransition(new Fade());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        final WaveView lxWaveView = root.findViewById(R.id.wave_lx);
        final WaveView rxWaveView = root.findViewById(R.id.wave_rx);
        final LinearLayoutCompat linearLayout = root.findViewById(R.id.linearLayout);
        final MaterialButton addNewLensesButton = root.findViewById(R.id.addNewLensesButton);
        final View userIcon = root.findViewById(R.id.userIcon);
        initUserInfo(root, userIcon);
        initWaveView(lxWaveView);
        initWaveView(rxWaveView);
        initButtonList(linearLayout);
        initAddNewLensesButton(addNewLensesButton);

        rxWaveHelper = new WaveHelper(rxWaveView, 0.0f);
        lxWaveHelper = new WaveHelper(lxWaveView, 0.0f);

        return root;
    }

    private void initButtonList(LinearLayoutCompat linearLayout) {
        View shop = linearLayout.findViewById(R.id.shop);
        View stocks = linearLayout.findViewById(R.id.stocks);
        View history = linearLayout.findViewById(R.id.history);

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder().addSharedElement(v, "sharedElem_homeshop").build();
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_storeFragment, null, null, extras);
            }
        });

        stocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToBSStocksFragment(stockNumber));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initUserInfo(final View root, final View userIcon) {
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new TSUserInfoDialog(requireContext(), requireActivity()).show();
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_TSUserInfoDialogFragment);
            }
        });
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
                if (!lensList.isEmpty()) {
                    LensesModel lenses = lensList.get(0);
                    Lens lxLens = new Lens(lenses.getInitDateLx(), lenses.getDurationLx(), lenses.getActiveLx());
                    Lens rxLens = new Lens(lenses.getInitDateRx(), lenses.getDurationRx(), lenses.getActiveRx());
                    activeLenses = new LensesWrapper(lxLens, rxLens, lenses.getId());
                    initLensesArea(view);
                    if (lensList.size() > 1) {
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
        MaterialTextView stockNum = view.findViewById(R.id.stockNumber);
        MaterialTextView subtitle = view.findViewById(R.id.stocksSubtitle);
        MaterialTextView daysLeft = view.findViewById(R.id.stocksSubtitle2);
        FrameLayout lensesLeft = view.findViewById(R.id.frameLayoutStockNumber);
        if (value >= 0) {
            lensesLeft.setVisibility(View.VISIBLE);
            daysLeft.setVisibility(View.VISIBLE);
            subtitle.setText("You still have left:");
            int days =  value / 2 * (activeLenses == null ? Duration.BIWEEKLY.getTime() : activeLenses.getLxLensDuration().getTime());
            stockNum.setText(String.format(Locale.getDefault(), "%d", value));
            daysLeft.setText(String.format(Locale.getDefault(), "They will last for another %d days", days));
        } else {
            lensesLeft.setVisibility(View.GONE);
            daysLeft.setVisibility(View.GONE);
            subtitle.setText("Click here to set your stock levels");
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

    private void initWaveView(WaveView waveView) {
        waveView.setShapeType(WaveView.ShapeType.SQUARE);
        waveView.setBorder(0, 0);
        waveView.setWaveColor(Color.parseColor("#285364e0"), Color.parseColor("#3C8b91ff"));
    }

    private void initHistoryPanel(View view, LensesWrapper lenses) {

    }

    private void initLensesArea(View view) {
        if (activeLenses.hasLensesActive()) {
            AppCompatTextView lxCountdown = view.findViewById(R.id.lx_countdown);
            AppCompatTextView lxExpDate = view.findViewById(R.id.lx_eta);
            AppCompatTextView rxCountdown = view.findViewById(R.id.rx_countdown);
            AppCompatTextView rxExpDate = view.findViewById(R.id.rx_eta);

            lxCountdown.setText(String.format(Locale.getDefault(), "%d", activeLenses.getLxLensRemainingTime()));
            lxExpDate.setText(activeLenses.getLxLensExpDate().toString(DateTimeFormat.shortDate()));
            lxWaveHelper.changeLevel(activeLenses.getLxLensRemainingTime() / (float) activeLenses.getLxLensDuration().getTime());
            rxCountdown.setText(String.format(Locale.getDefault(), "%d", activeLenses.getRxLensRemainingTime()));
            rxExpDate.setText(activeLenses.getRxLensExpDate().toString(DateTimeFormat.shortDate()));
            rxWaveHelper.changeLevel(activeLenses.getRxLensRemainingTime() / (float) activeLenses.getRxLensDuration().getTime());
        }
    }

}
