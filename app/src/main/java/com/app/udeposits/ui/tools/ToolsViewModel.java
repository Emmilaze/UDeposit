package com.app.udeposits.ui.tools;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.udeposits.R;
import com.app.udeposits.core.UtilPercents;
import com.app.udeposits.ui.slideshow.SlideshowViewModel;

public class ToolsViewModel extends ViewModel {

    public static void calculate(View view){
        EditText first = view.findViewById(R.id.first);
        int money = Integer.parseInt(first.getText().toString());

        EditText percentText = view.findViewById(R.id.percent);
        float percent = Float.parseFloat(percentText.getText().toString());

        EditText daysText = view.findViewById(R.id.days);
        int days = Integer.parseInt(daysText.getText().toString());

        CheckBox checkBox = view.findViewById(R.id.checkBox);
        TextView textView = view.findViewById(R.id.sum);
        if (checkBox.isSelected())
            textView.setText(UtilPercents.getDifficultPercents(money, percent, days));
        else
            textView.setText(UtilPercents.getSimplePercents(money, percent, days));
    }
}