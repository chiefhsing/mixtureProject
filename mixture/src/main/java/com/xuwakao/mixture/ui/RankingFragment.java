package com.xuwakao.mixture.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xuwakao.mixture.R;

/**
 * Created by duowan on 13-10-9.
 */
public class RankingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
        ((ImageView)rootView).setImageResource(R.drawable.jupiter);
        return rootView;
    }
}
