package com.xuwakao.mixture.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xuwakao.mixture.R;

import net.simonvt.menudrawer.MenuDrawer;

/**
 * Created by xujiexing on 13-10-8.
 */
public class MenuDrawerActivity extends ActionBarActivity {

    private MenuDrawer mDrawer;
    private ListView menuList;
    private String[] mPlanetTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_drawer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawer = (MenuDrawer) findViewById(R.id.drawer);
        mDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
        // The drawable that replaces the up indicator in the action bar
        mDrawer.setupUpIndicator(this);
        mDrawer.setSlideDrawable(R.drawable.ic_drawer);
        // Whether the previous drawable should be shown
        mDrawer.setDrawerIndicatorEnabled(true);

        menuList = (ListView) findViewById(R.id.menu_list);
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        menuList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, R.id.item_title, mPlanetTitles));
        // Set the list's click listener
        menuList.setOnItemClickListener(new DrawerItemClickListener());

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.toggleMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        if (position <= 7) {
            // Create a new fragment and specify the planet to show based on position
            Fragment fragment = new MainActivity.PlanetFragment();
            Bundle args = new Bundle();
            args.putInt(MainActivity.PlanetFragment.ARG_PLANET_NUMBER, position);
            fragment.setArguments(args);

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mdContent, fragment).commit();

            // Highlight the selected item, update the title, and close the drawer
            menuList.setItemChecked(position, true);
            setTitle(mPlanetTitles[position]);
            mDrawer.closeMenu();
        } else if (position == 8) {
        }
    }

}
