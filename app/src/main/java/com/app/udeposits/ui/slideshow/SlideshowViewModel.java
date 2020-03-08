package com.app.udeposits.ui.slideshow;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.ViewModel;

import com.app.udeposits.R;
import com.app.udeposits.core.deposit.DepositManager;

import java.util.List;

public class SlideshowViewModel extends ViewModel {

    private DepositManager manager = new DepositManager();
    private List<String> banks = manager.getBankNames();
    private final String[] currency = {"UAH", "USD", "EURO"};
    private final String[] data = {"", "ежемесячно", "в конце срока", "капитализация"};

    public void fillBankNames(Context c, View root) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, banks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = root.findViewById(R.id.bankNames);
        spinner.setAdapter(adapter);
    }

    public void fillCurrency(Context c, View root) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, currency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = root.findViewById(R.id.currencyList);
        spinner.setAdapter(adapter);
    }

    public void fillPayout(Context c, View root) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = root.findViewById(R.id.payoutList);
        spinner.setAdapter(adapter);
    }

    public static String[] getFields(View root) {
        String[] fields = new String[7];

        Spinner bankList = root.findViewById(R.id.bankNames);
        fields[0] = bankList.getSelectedItem().toString();

        Spinner currencyList = root.findViewById(R.id.currencyList);
        fields[1] = currencyList.getSelectedItem().toString();

        EditText percentField = root.findViewById(R.id.printPercent);
        fields[2] = percentField.getText().toString();

        EditText percentField2 = root.findViewById(R.id.printPercent2);
        fields[3] = percentField2.getText().toString();

        EditText periodField = root.findViewById(R.id.printPeriod);
        fields[4] = periodField.getText().toString();
        EditText periodField2 = root.findViewById(R.id.printPeriod2);
        fields[5] = periodField2.getText().toString();

        Spinner payoutSpinner = root.findViewById(R.id.payoutList);
        fields[6] = payoutSpinner.getSelectedItem().toString();

        return fields;
    }
}