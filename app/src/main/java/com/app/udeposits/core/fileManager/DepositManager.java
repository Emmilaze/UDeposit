package com.app.udeposits.core.fileManager;

import android.app.Activity;

import com.app.udeposits.R;
import com.app.udeposits.core.Deposit;
import com.app.udeposits.ui.gallery.GalleryFragment;
import com.app.udeposits.ui.gallery.GalleryViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DepositManager {
    private FileManager fileManager;
    public static List<Deposit> uahDeposits;
    public static List<Deposit> usdDeposits;
    public static List<Deposit> euroDeposits;
    public static List<Deposit> allDeposits;

    public DepositManager() {
        fileManager = new FileManager();
    }

    public List<Deposit> getDeposits() {
        switch (GalleryFragment.currency) {
            case "UAH":
                return uahDeposits;
            case "USD":
                return usdDeposits;
            case "EURO":
                return euroDeposits;
        }
        return null;
    }

    public void fillLists(Activity activity) {
        if (uahDeposits == null)
            uahDeposits = fileManager.readGSON(activity, R.raw.uah);
        if (usdDeposits == null)
            usdDeposits = fileManager.readGSON(activity, R.raw.usd);
        if (euroDeposits == null)
            euroDeposits = fileManager.readGSON(activity, R.raw.euro);
        allDeposits = new ArrayList();
        allDeposits.addAll(uahDeposits);
        allDeposits.addAll(usdDeposits);
        allDeposits.addAll(euroDeposits);
    }

    public List<String> getBankNames() {
        Set<String> names = new HashSet<>();
        names.add("");
        for (Deposit deposit : allDeposits) {
            names.add(deposit.getBankName());
        }
        return new ArrayList<>(names);
    }

    public List<Deposit> searchDeposit(final String[] fields) {
        List<Deposit> currentDeposits = allDeposits;
        switch (fields[1]) {
            case "UAH":
                currentDeposits = uahDeposits;
                break;
            case "USD":
                currentDeposits = usdDeposits;
                break;
            case "EURO":
                currentDeposits = euroDeposits;
                break;
        }
        Stream<Deposit> stream = currentDeposits.stream();
        if (!fields[0].isEmpty())
            stream = stream.filter(deposit -> deposit.getBankName()
                    .equals(fields[0]));
        if (!fields[2].isEmpty() && !fields[3].isEmpty())
            stream = stream.filter(deposit -> (deposit.getPercent() >= Float.parseFloat(fields[2])
                    && deposit.getPercent() <= Float.parseFloat(fields[3])));
        if (!fields[4].isEmpty() && !fields[5].isEmpty())
            stream = stream.filter(deposit -> (deposit.getDurationInt() >= Integer.parseInt(fields[4])
                    && deposit.getDurationInt() <= Integer.parseInt(fields[5])));
        if (!fields[6].isEmpty())
            stream = stream.filter(deposit -> deposit.getPayout().contains(fields[6]));
        return stream.collect(Collectors.toList());
    }
}