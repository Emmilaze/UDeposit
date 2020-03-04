package com.app.udeposits.ui.gallery;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.app.udeposits.R;

import de.codecrafters.tableview.TableView;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private static View root;
    public static String currency = "UAH";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryViewModel.printTable(root);
        return root;
    }

    public static void getMoreInfo(){

    }

    public static void goToCalc(){
        TableView tableView = root.findViewById(R.id.tableView);

    }
}