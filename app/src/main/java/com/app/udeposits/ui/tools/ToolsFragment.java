package com.app.udeposits.ui.tools;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.app.udeposits.R;
import com.app.udeposits.core.deposit.Deposit;
import com.app.udeposits.core.NotEnoughDataException;
import com.app.udeposits.ui.gallery.GalleryFragment;
import com.app.udeposits.ui.share.ShareViewModel;

import static com.app.udeposits.core.deposit.DepositManager.euroDeposits;
import static com.app.udeposits.core.deposit.DepositManager.uahDeposits;
import static com.app.udeposits.core.deposit.DepositManager.usdDeposits;
import static com.app.udeposits.ui.share.ShareViewModel.index;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private static View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        root = inflater.inflate(R.layout.fragment_tools, container, false);

        if (GalleryFragment.clicked)
            fillFields(root);
        return root;
    }

    public static void calculate() {
        try {
            ToolsViewModel.calculate(root);
        } catch (NotEnoughDataException e) {
            Toast toast = Toast.makeText(root.getContext(),
                    "Заполните все поля!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        }
    }

    private void fillFields(View view) {
        Deposit deposit = null;
        if (ShareViewModel.search != null)
            deposit = ShareViewModel.search.get(index);
        else {

            switch (GalleryFragment.currency) {
                case "UAH":
                    deposit = uahDeposits.get(index);
                    break;
                case "USD":
                    deposit = usdDeposits.get(index);
                    break;
                case "EURO":
                    deposit = euroDeposits.get(index);
            }
        }

        EditText percentText = view.findViewById(R.id.percent);
        percentText.setText(String.valueOf(deposit.getPercent()));
        EditText daysText = view.findViewById(R.id.days);
        daysText.setText(Integer.toString(deposit.getDurationInt()));
        CheckBox checkBox = view.findViewById(R.id.checkBox);
        if (deposit.getPayout().contains("капитализация"))
            checkBox.setSelected(true);
    }
}