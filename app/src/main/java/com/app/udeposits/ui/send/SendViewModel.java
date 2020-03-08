package com.app.udeposits.ui.send;

import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.app.udeposits.R;
import com.app.udeposits.core.deposit.Deposit;
import com.app.udeposits.core.deposit.DepositManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PreviewColumnChartView;

public class SendViewModel extends ViewModel {

    private final String[] currency = {"UAH", "USD", "EURO"};
    private ColumnChartView chart;
    private PreviewColumnChartView previewChart;
    private ColumnChartData data;
    private ColumnChartData previewData;
    private List<Deposit> currentList;

    public void printGraph(View view) {
        fillSpinners(view);
    }

    private void fillSpinners(View view) {
        chart = view.findViewById(R.id.chart);
        previewChart = view.findViewById(R.id.preview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, currency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner currencySpinner = view.findViewById(R.id.currencySpinner);
        currencySpinner.setAdapter(adapter);

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case (0):
                        fillPeriodSpinner(view, DepositManager.getAllUAHPeriods(), position);
                        break;
                    case (1):
                        fillPeriodSpinner(view, DepositManager.getAllUSDPeriods(), position);
                        break;
                    case (2):
                        fillPeriodSpinner(view, DepositManager.getAllEUROPeriods(), position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void fillPeriodSpinner(View view, Set<Integer> list, int row) {
        List<Integer> arrayList = new ArrayList<>(list);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner periodSpinner = view.findViewById(R.id.periodSpinner);
        periodSpinner.setAdapter(adapter);

        periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int period = arrayList.get(position);
                fillChart(row, period);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    private void fillChart(int position, int period) {
        currentList = null;
        switch (position) {
            case (0):
                currentList = DepositManager.uahDeposits
                        .stream()
                        .filter(deposit -> deposit.getDurationInt() == period)
                        .collect(Collectors.toList());
                break;
            case (1):
                currentList = DepositManager.usdDeposits.stream()
                        .filter(deposit -> deposit.getDurationInt() == period)
                        .collect(Collectors.toList());
                break;
            case (2):
                currentList = DepositManager.euroDeposits.stream()
                        .filter(deposit -> deposit.getDurationInt() == period)
                        .collect(Collectors.toList());
                break;
        }

        List<Column> columns = new ArrayList<>();
        for (Deposit deposit : currentList) {
            List<SubcolumnValue> values = new ArrayList<>();
            values.add(new SubcolumnValue(deposit.getPercent(), ChartUtils.pickColor()));
            columns.add(new Column(values));
        }
        data = new ColumnChartData(columns);
        data.setAxisXBottom(new Axis());
        data.setAxisYLeft(new Axis().setHasLines(true));
        previewData = new ColumnChartData(data);
        chart.setColumnChartData(data);
        chart.setScrollEnabled(true);
        chart.setZoomEnabled(true);
        chart.setValueSelectionEnabled(true);
        chart.setOnValueTouchListener(new ValueTouchListener());
        for (Column column : previewData.getColumns()) {
            for (SubcolumnValue subcolumn : column.getValues()) {
                subcolumn.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
            }
        }
        previewChart.setColumnChartData(previewData);
        previewChart.setViewportChangeListener(new ViewPortListener());
        previewXY();
    }

    private void previewXY() {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        tempViewport.inset(tempViewport.width() / 4.0f, tempViewport.height() / 4.0f);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
        previewChart.setZoomType(ZoomType.VERTICAL);
    }

    private class ViewPortListener implements ViewportChangeListener {

        @Override
        public void onViewportChanged(Viewport viewport) {
            chart.setCurrentViewport(viewport);
        }
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {
            Deposit deposit = currentList.get(i);
            Toast toast = Toast.makeText(SendFragment.root.getContext(),
                    deposit.getBankName() + ": " + deposit.getPercent() + " %",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        }

        @Override
        public void onValueDeselected() {
        }
    }
}