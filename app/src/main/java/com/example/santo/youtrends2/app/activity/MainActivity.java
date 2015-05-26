package com.example.santo.youtrends2.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.santo.youtrends2.app.R;
import com.example.santo.youtrends2.app.fragments.NavigationDrawerFragment;
import com.example.santo.youtrends2.app.fragments.TabCustomFragment;
import com.example.santo.youtrends2.app.fragments.TabFragment;
import com.example.santo.youtrends2.app.listeners.FilterTrendListener;
import com.example.santo.youtrends2.app.tab.SlidingTabLayout;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;



public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private SlidingTabLayout mTabs;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FloatingActionMenu actionMenu;
    private FloatingActionButton actionButton;
    public static final int TAB_GENERALE = 0;
    public static final int TAB_MUSICA = 1;
    public static final int TAB_TECNOLOGIA = 2;
    public static final int TAB_VIDEOGAMES = 3;
    public static final int TAB_SPORT = 4;
    public static final int TAB_CUSTOM = 5;
    private static final String TAG_SHOW_POSITIVE = "showPositive";
    private static final String TAG_SHOW_NEW= "showNew";
    private static final String TAG_SHOW_ALL = "showAll";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildTabs();

        buildNavigationDrawer();

        buildFloatingActionButton();
    }

    public void onDrawerItemClicked(int index) {
        viewPager.setCurrentItem(index);

    }

    private void buildNavigationDrawer() {
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    private void buildTabs() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabs = (SlidingTabLayout)findViewById(R.id.materialTabHost);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(viewPager);
        //mTabs.setBackgroundColor(p);
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.accentColor));
    }

    private void buildFloatingActionButton() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_action_new);

        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .setBackgroundDrawable(R.drawable.selector_button_fucsia)
                .build();

        ImageView iconShowPositive = new ImageView(this);
        iconShowPositive.setImageResource(R.drawable.ic_action_alphabets);
        ImageView iconShowNew = new ImageView(this);
        iconShowNew.setImageResource(R.drawable.ic_action_calendar);
        ImageView iconShowAll = new ImageView(this);
        iconShowAll.setImageResource(R.drawable.ic_action_important);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_blue));

        SubActionButton buttonShowPositive = itemBuilder.setContentView(iconShowPositive).build();
        SubActionButton buttonShowNew = itemBuilder.setContentView(iconShowNew).build();
        SubActionButton buttonShowAll = itemBuilder.setContentView(iconShowAll).build();

        buttonShowPositive.setTag(TAG_SHOW_POSITIVE);
        buttonShowNew.setTag(TAG_SHOW_NEW);
        buttonShowAll.setTag(TAG_SHOW_ALL);

        buttonShowPositive.setOnClickListener(this);
        buttonShowNew.setOnClickListener(this);
        buttonShowAll.setOnClickListener(this);

        actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonShowPositive)
                .addSubActionView(buttonShowNew)
                .addSubActionView(buttonShowAll)
                .attachTo(actionButton)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = (Fragment) adapter.instantiateItem(viewPager, viewPager.getCurrentItem());
        if (fragment instanceof FilterTrendListener) {

            if (v.getTag().equals(TAG_SHOW_POSITIVE)) {
                ((FilterTrendListener) fragment).onShowPositive();
            }
            if (v.getTag().equals(TAG_SHOW_NEW)) {
                ((FilterTrendListener) fragment).onShowNew();
            }
            if (v.getTag().equals(TAG_SHOW_ALL)) {
                ((FilterTrendListener) fragment).onShowAll();
            }
        }
    }

    public void  toggleTranslateFloatingActionButton(float slideOffset) {
        if (actionMenu != null) {
            if (actionMenu.isOpen()) {
                actionMenu.close(true);
            }
            actionButton.setTranslationX(slideOffset * 200);
        }
    }

    public void onDrawerSlide (float slideOffset) {
        toggleTranslateFloatingActionButton (slideOffset);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        FragmentManager fragmentManager;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (position){
                case TAB_GENERALE:
                    fragment = TabFragment.newInstance(0, 50, "IT");
                    break;
                case TAB_MUSICA:
                    fragment = TabFragment.newInstance(10, 50, "IT");
                    break;
                case TAB_TECNOLOGIA:
                    fragment = TabFragment.newInstance(20, 50, "IT");
                    break;
                case TAB_VIDEOGAMES:
                    fragment = TabFragment.newInstance(28, 50, "IT");
                    break;
                case TAB_SPORT:
                    fragment = TabFragment.newInstance(17, 50, "IT");
                    break;
                case TAB_CUSTOM:
                    fragment = TabCustomFragment.newInstance("","");
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {

            return getResources().getStringArray(R.array.drawer_tabs).length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.drawer_tabs)[position].toUpperCase();
        }
    }

}
