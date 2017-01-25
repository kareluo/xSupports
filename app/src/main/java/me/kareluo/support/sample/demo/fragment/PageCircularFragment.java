package me.kareluo.support.sample.demo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.kareluo.support.sample.R;
import me.kareluo.support.view.CircularImageView;

/**
 * Created by felix on 17/1/23.
 */
public class PageCircularFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_demo_circular, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        CircularImageView cImageView = (CircularImageView) view.findViewById(R.id.civ_3);
        cImageView.setRingColor(Color.BLACK);
        cImageView.setRingWidth(6);
        cImageView.setRingPadding(6);
    }
}
