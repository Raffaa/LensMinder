package it.raffinato.dev.lensminder.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import it.raffinato.dev.lensminder.R;

public class LensSwitch extends FrameLayout {

    private final boolean equalSelected = false;
    //Switch Components
    private SwitchItem lx, rx;
    private ImageView equal;
    //Colors
    private int selectedColor, notSelectedColor;
    //ViewPager
    private ViewPager2 vp;

    public LensSwitch(Context context) {
        super(context);
        init();
    }

    public LensSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LensSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.lens_switch_layout, this);

        lx = findViewById(R.id.left);
        rx = findViewById(R.id.right);
        //equal = findViewById(R.id.equal);
        setColors();
        setListeners();
    }

    public void setViewPager(ViewPager2 viewPager) {
        this.vp = viewPager;
    }

    private void setColors() {
        selectedColor = getResources().getColor(R.color.secondaryColor, null);
        notSelectedColor = getResources().getColor(R.color.primaryTextColor, null);
    }

    private void setListeners() {
        lxListener();
        rxListener();
        //equalListener();
    }
/*
    private void equalListener() {
        equal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equalSelected) {
                    lx.select();
                    rx.deselect();
                    equal.setImageTintList(ColorStateList.valueOf(notSelectedColor));
                } else {
                    lx.disable();
                    rx.disable();
                    equal.setImageTintList(ColorStateList.valueOf(selectedColor));
                }
                if (areViewsSet()) {
                    showLeftView();
                }
                equalSelected = !equalSelected;
            }
        });
    }
 */

    private void rxListener() {
        rx.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lx.deselect();
                rx.select();
                if (areViewsSet()) {
                    showRightView();
                }
            }
        });
    }

    private void lxListener() {
        lx.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lx.select();
                rx.deselect();
                if (areViewsSet()) {
                    showLeftView();
                }
            }
        });
    }

    private boolean areViewsSet() {
        return this.vp != null && this.vp.getAdapter() != null && this.vp.getAdapter().getItemCount() == 2;
    }

    private void showLeftView() {
        this.vp.setCurrentItem(0, true);
    }

    private void showRightView() {
        this.vp.setCurrentItem(1, true);
    }

    public boolean isEqualSelected() {
        return equalSelected;
    }
}
