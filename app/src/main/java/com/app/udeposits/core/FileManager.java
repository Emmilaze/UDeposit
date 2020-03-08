package com.app.udeposits.core;

import android.app.Activity;

import com.app.udeposits.core.deposit.Deposit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FileManager {
    private static Gson gson;

    public FileManager() {
        gson = new Gson();
    }

    public List<Deposit> readGSON(Activity activity, int f) {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = activity.getResources().openRawResource(f);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String data = "";
            try {
                while ((data = br.readLine()) != null) {
                    sb.append(data);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Deposit> deposits = gson.fromJson(sb.toString(), new TypeToken<List<Deposit>>() {
        }.getType());
        return deposits;
    }
}
