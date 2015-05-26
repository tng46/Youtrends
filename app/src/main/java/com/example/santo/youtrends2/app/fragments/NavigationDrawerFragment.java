package com.example.santo.youtrends2.app.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.santo.youtrends2.app.R;
import com.example.santo.youtrends2.app.activity.MainActivity;
import com.example.santo.youtrends2.app.adapters.DrawerAdapter;
import com.example.santo.youtrends2.app.listeners.ClickListener;
import com.example.santo.youtrends2.app.pojo.Information;
import com.example.santo.youtrends2.app.listeners.RecyclerTouchListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME ="testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrowerToggle;
    private DrawerLayout mDrawerLayout;
    private DrawerAdapter adapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer= Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if (savedInstanceState!=null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new DrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                ((MainActivity)getActivity()).onDrawerItemClicked(position-1);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return layout;
    }

    public List<Information> getData() {
        List<Information> data = new ArrayList();
        int[] icons = {
                R.drawable.i01generale,
                R.drawable.i02music,
                R.drawable.i03videogiochi,
                R.drawable.i04tecnologia,
                R.drawable.i05sport,
                R.drawable.i06custom
        };
        String[] titles = getResources().getStringArray(R.array.drawer_tabs);

        for (int i=0; i< titles.length ; i++) {
            Information current = new Information();
            current.setIconId(icons[i%icons.length]);
            current.setTitle(titles[i%titles.length]);
            data.add(current);

        }

        return data;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final android.support.v7.widget.Toolbar toolBar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrowerToggle = new ActionBarDrawerToggle(getActivity(),
                                                    drawerLayout,
                                                    toolBar,
                                                    R.string.drawer_open,
                                                    R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");
                }
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                ((MainActivity)getActivity()).onDrawerSlide(slideOffset);

                toolBar.setAlpha(1 - slideOffset / 2);

            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrowerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrowerToggle.syncState();
                if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
                    mDrawerLayout.openDrawer(containerView);
                }
            }
        });


    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences (Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }




}

