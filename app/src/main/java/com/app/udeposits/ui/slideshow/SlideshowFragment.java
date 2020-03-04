package com.app.udeposits.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.app.udeposits.R;

public class SlideshowFragment extends Fragment {
    private SlideshowViewModel slideshowViewModel;
    public static View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        slideshowViewModel.fillBankNames(this.getContext(), root);
        slideshowViewModel.fillCurrency(this.getContext(), root);
        slideshowViewModel.fillPayout(this.getContext(), root);

        return root;
    }
}