package com.app.udeposits.ui.share;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.app.udeposits.R;
import com.app.udeposits.core.deposit.Deposit;
import com.app.udeposits.ui.gallery.GalleryFragment;

import java.util.List;

import static com.app.udeposits.core.deposit.DepositManager.euroDeposits;
import static com.app.udeposits.core.deposit.DepositManager.uahDeposits;
import static com.app.udeposits.core.deposit.DepositManager.usdDeposits;

public class ShareViewModel extends ViewModel {

    public static int index;
    public static List<Deposit> search;

    public void setFields(View root) {
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

        TextView name = root.findViewById(R.id.nameField);
        name.setText(deposit.getDepositName());

        TextView bank = root.findViewById(R.id.bankField);
        bank.setText(deposit.getBankName());

        TextView currency = root.findViewById(R.id.currencyField);
        currency.setText(deposit.getCurrency());

        TextView percent = root.findViewById(R.id.percentField);
        percent.setText(String.valueOf(deposit.getPercent()));

        TextView duration = root.findViewById(R.id.durationField);
        duration.setText(deposit.getDuration());

        TextView payout = root.findViewById(R.id.payoutField);
        payout.setText(deposit.getPayout());
    }
}