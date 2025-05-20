package com.example.appxemphim.UI.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;

public class FilterChipGroupView extends LinearLayout {
    private ChipGroup chipGroup;

    public FilterChipGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        chipGroup = new ChipGroup(context);
        addView(chipGroup);
    }

    public void setStringItems(List<String> items) {
        chipGroup.removeAllViews();
        for (String item : items) {
            Chip chip = new Chip(getContext());
            chip.setText(item);
            chip.setCheckable(true);
            chipGroup.addView(chip);
        }
    }
    public List<String> getStringSelected() {
        List<String> selected = new ArrayList<>();
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (chip.isChecked()) {
                selected.add(chip.getText().toString());
            }
        }
        return selected;
    }
    public void setStringSelected(List<String> selectedItems) {
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (selectedItems.contains(chip.getText().toString())) {
                chip.setChecked(true);
            } else {
                chip.setChecked(false);
            }
        }
    }

    public void setIntegerItems(List<Integer> items) {
        chipGroup.removeAllViews();
        for (int item : items) {
            Chip chip = new Chip(getContext());
            chip.setText(item);
            chip.setCheckable(true);
            chipGroup.addView(chip);
        }
    }
    public void setIntegerSelected(List<Integer> selectedItems) {
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (selectedItems.contains(chip.getText().toString())) {
                chip.setChecked(true);
            } else {
                chip.setChecked(false);
            }
        }
    }



    public void clearSelected() {
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setChecked(false);
        }
    }
}
