package it.raffinato.dev.lensminder.utils.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.textview.MaterialTextView;

import it.raffinato.dev.lensminder.R;

public class SwitchItem extends LinearLayoutCompat {

    private boolean selected = false;

    private MaterialTextView text;
    private AppCompatImageView indicator;

    private int selectedColor, notSelectedColor, disabledColor;

    public SwitchItem(Context context) {
        super(context);
        init(null);
    }

    public SwitchItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SwitchItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.switch_item, this);

        text = findViewById(R.id.text);
        indicator = findViewById(R.id.indicator);
        selectedColor = getResources().getColor(R.color.secondaryColor, null);
        notSelectedColor = getResources().getColor(R.color.primaryTextColor, null);
        disabledColor = getResources().getColor(R.color.primaryDarkColor, null);
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.SwitchItem);
            text.setText(attributes.getString(R.styleable.SwitchItem_text));
            boolean selected = attributes.getBoolean(R.styleable.SwitchItem_selected, false);
            if (selected) {
                select();
            }
            attributes.recycle();
        }
    }

    public void setSelected(boolean selected) {
        if (selected) {
            select();
        } else {
            deselect();
        }
    }

    public void select() {
        selected = true;
        setEnabled(false);
        setColor(selectedColor);
    }

    public void deselect() {
        selected = false;
        setEnabled(true);
        setColor(notSelectedColor);
    }

    public void disable() {
        setColor(disabledColor);
        setEnabled(false);
    }

    private void setColor(int color) {
        text.setTextColor(color);
        indicator.setImageTintList(ColorStateList.valueOf(color));
    }
}
