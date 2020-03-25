package it.raffinato.dev.lensminder.utils.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import it.raffinato.dev.lensminder.R;

public class LensSwitch extends FrameLayout {

    //Switch Components
    private SwitchItem lx, rx;
    private ImageView equal;
    //Colors
    private int selectedColor, notSelectedColor;
    //Pages
    private View leftView, rightView;

    private boolean equalSelected = false;

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
        equal = findViewById(R.id.equal);
        setColors();
        setListeners();
    }

    public void setViews(View left, View right) {
        this.leftView = left;
        this.rightView = right;
    }

    private void setColors() {
        selectedColor = getResources().getColor(R.color.secondaryColor, null);
        notSelectedColor = getResources().getColor(R.color.primaryTextColor, null);
    }

    private void setListeners() {
        lxListener();
        rxListener();
        equalListener();
    }

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
        return leftView != null && rightView != null;
    }

    private void showLeftView() {
        leftView.setVisibility(VISIBLE);
        rightView.setVisibility(GONE);
    }

    private void showRightView() {
        leftView.setVisibility(GONE);
        rightView.setVisibility(VISIBLE);
    }

    public boolean isEqualSelected() {
        return equalSelected;
    }
}
