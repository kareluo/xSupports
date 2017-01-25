package me.kareluo.support.sample.demo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

import me.kareluo.support.sample.R;
import me.kareluo.support.view.LabelView;

/**
 * Created by felix on 17/1/23.
 */
public class PageTagFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_demo_tag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final LabelView labelView = (LabelView) view.findViewById(R.id.lv_label);

        view.findViewById(R.id.btn_toggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelView.toggle();
            }
        });

        view.findViewById(R.id.btn_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelView.setBorderColor(Color.RED);
            }
        });

        view.findViewById(R.id.lv_label2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Checkable) v).toggle();
            }
        });
    }
}
