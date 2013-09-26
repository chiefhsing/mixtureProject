package com.xuwakao.mixture.ui;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageButton;

import com.xuwakao.mixture.R;

public class MainOverFlowProvider extends ActionProvider implements
        OnMenuItemClickListener {

    static final int LIST_LENGTH = 3;

    Context mContext;

    public MainOverFlowProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    public View onCreateActionView(MenuItem forItem) {
        // Inflate the action view to be shown on the action bar.
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.action_provider, null);
        ImageButton button = (ImageButton) view.findViewById(R.id.more_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something...
            }
        });
        return view;
    }

    @Override
    public boolean onPerformDefaultAction() {
        return super.onPerformDefaultAction();
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();

        subMenu.add(0, 1, 1, mContext.getString(R.string.action_star))
                .setIcon(R.drawable.ic_action_star).setOnMenuItemClickListener(this);

        subMenu.add(0, 2, 1, mContext.getString(R.string.action_video))
                .setIcon(R.drawable.ic_action_video).setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId())
        {
            case 1:
                break;
            case 2:
                break;

        }

        return true;
    }
}
