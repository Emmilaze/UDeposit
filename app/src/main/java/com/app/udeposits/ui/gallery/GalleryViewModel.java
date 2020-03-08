package com.app.udeposits.ui.gallery;

import android.graphics.Color;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.app.udeposits.R;
import com.app.udeposits.core.deposit.Deposit;
import com.app.udeposits.core.deposit.DepositManager;
import com.app.udeposits.ui.share.ShareViewModel;

import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class GalleryViewModel extends ViewModel {
    private DepositManager manager = new DepositManager();
    public static String[] fields;


    private String[] headers = {"Банк", "%", "Период", "Выплата"};
    private String[][] column;

    public String[] getHeaders() {
        return headers;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String[][] fillTable() {
        List<Deposit> deposits;
        if (fields == null)
            deposits = manager.getDeposits();
        else
            deposits = manager.searchDeposit(fields);
        Deposit d;
        column = new String[deposits.size()][4];
        for (int i = 0; i < deposits.size(); i++) {
            d = deposits.get(i);
            column[i][0] = d.getBankName();
            column[i][1] = String.valueOf(d.getPercent());
            column[i][2] = d.getDuration();
            column[i][3] = d.getPayout();
        }
        return column;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void printTable(View root) {
        TableView<String[]> tb = root.findViewById(R.id.tableView);
        tb.setColumnCount(4);
        tb.setHeaderBackgroundColor(Color.parseColor("#e0e8e5"));
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(root.getContext(), getHeaders()));
        tb.setDataAdapter(new SimpleTableDataAdapter(root.getContext(), fillTable()));


        tb.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                ShareViewModel.index = rowIndex;
                GalleryFragment.clicked = true;
            }
        });
    }
}