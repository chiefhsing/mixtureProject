package com.xuwakao.mixture.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuwakao.mixture.R;
import com.xuwakao.mixture.framework.utils.MLog;

/**
 * Created by xujiexing on 13-10-9.
 */
public class ActionBarTabActivity extends UIActionBarActivity {
    private static final String TAG = MLog.makeLogTag(ActionBarTabActivity.class);

    private ActionBar actionBar;
    private String[] tabsTitle = new String[]{};
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_action_bar_tabs);
        this.actionBar = getSupportActionBar();
        this.actionBar.setDisplayHomeAsUpEnabled(true);

        this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        this.actionBar.setDisplayShowTitleEnabled(false);

        this.addTabs();

        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
        this.viewPager.setAdapter(new EntertainmentFragementAdapter(getSupportFragmentManager()));

        this.viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        MLog.verbose(TAG, "ActionBarTabActivity onSaveInstanceState called");
    }

    private void addTabs() {
        tabsTitle = getResources().getStringArray(R.array.main_tab);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(tabsTitle[0])
                .setTabListener(new ActionBarTabListener<RankingFragment>(
                        this, tabsTitle[0], RankingFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(tabsTitle[1])
                .setTabListener(new ActionBarTabListener<MusicFragment>(
                        this, tabsTitle[1], MusicFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(tabsTitle[2])
                .setTabListener(new ActionBarTabListener<ArtistFragment>(
                        this, tabsTitle[2], ArtistFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(tabsTitle[3])
                .setTabListener(new ActionBarTabListener<AlbumFragment>(
                        this, tabsTitle[3], AlbumFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(tabsTitle[4])
                .setTabListener(new ActionBarTabListener<AlbumFragment>(
                        this, tabsTitle[4], AlbumFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(tabsTitle[5])
                .setTabListener(new ActionBarTabListener<AlbumFragment>(
                        this, tabsTitle[5], AlbumFragment.class));
        actionBar.addTab(tab);
    }

    private class EntertainmentFragementAdapter extends FragmentPagerAdapter{

        public EntertainmentFragementAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the Fragment associated with a specified position.
         */
        @Override
        public Fragment getItem(int position) {
            // The other sections of the app are dummy placeholders.
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return tabsTitle.length;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


}
