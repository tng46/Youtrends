package com.example.santo.youtrends2.app.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.santo.youtrends2.app.R;
import com.example.santo.youtrends2.app.adapters.TrendsAdapter;
import com.example.santo.youtrends2.app.json.EndPoints;
import com.example.santo.youtrends2.app.json.Parser;
import com.example.santo.youtrends2.app.listeners.FilterTrendListener;
import com.example.santo.youtrends2.app.network.VolleySingleton;
import com.example.santo.youtrends2.app.pojo.Trend;
import com.example.santo.youtrends2.app.youtrends.MyApplication;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment extends Fragment implements FilterTrendListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_PARAM1 = "categoryId";
    private static final String ARG_PARAM2 = "limit";
    private static final String ARG_PARAM3 = "region";
    private static final String STATE_TRENDS = "state_movies";
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<Trend> listTrends = new ArrayList<>();
    private TrendsAdapter trendsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView listViewTrends;
    private TextView textError;

    private String region;
    private int limit;
    private int categoryId;
    private boolean areLecturesLoaded = false;
    Bundle savedInstanceState;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoryId Parameter 1.
     * @param limit Parameter 2.
     * @param region Parameter 3.
     * @return A new instance of fragment TabFragment.
     */
    public static TabFragment newInstance(int categoryId, int limit, String region) {

        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, categoryId);
        args.putInt(ARG_PARAM2, limit);
        args.putString(ARG_PARAM3, region);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_TRENDS, listTrends);
    }

    public TabFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ARG_PARAM1);
            limit = getArguments().getInt(ARG_PARAM2);
            region = getArguments().getString(ARG_PARAM3);
        }

        volleySingleton = VolleySingleton.getmInstance();
        requestQueue = volleySingleton.getRequestQueue();
        //sendJsonRequest();
        //setUserVisibleHint(true);
    }

    private void sendJsonRequest() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                EndPoints.getRequestURL(categoryId, limit, region),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {

                        textError.setVisibility(View.GONE);

                        listTrends = Parser.parseJSONResponse(response);
                        int count=0;
                        for (int i=0;i<listTrends.size(); i++){
                            listTrends.get(i).setRegion(region);
                            listTrends.get(i).setCategoryId(categoryId);


                            int trendDifference = MyApplication.getWritableDatabase().getLastPositionToSetDifference(
                                    listTrends.get(i).getId(), listTrends.get(i).getCategoryId(), listTrends.get(i).getRegion())
                                    - Integer.parseInt(listTrends.get(i).getPosition());
                            listTrends.get(i).setTrendDifference(trendDifference);
                            if (trendDifference==0) count++;
                        }

                        MyApplication.getWritableDatabase().insertTrends(listTrends, false);
                        trendsAdapter.setTrendList(listTrends);

                        if (count==listTrends.size()) {
                            Toast.makeText(getActivity(),"No Updates Found",Toast.LENGTH_SHORT).show();
                            //listTrends=MyApplication.getWritableDatabase().readTrends(categoryId, limit, region, "");
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        handleVolleyError(error);
                    }
                });

        requestQueue.add(request);
    }

    private void handleVolleyError (VolleyError error) {
        textError.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            textError.setText(R.string.error_timeout);

        } else if (error instanceof AuthFailureError) {
            //TODO;

        } else if (error instanceof ServerError) {
            //TODO;

        } else if (error instanceof NetworkError) {
            //TODO;

        } else if (error instanceof ParseError) {
            //TODO;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        textError = (TextView) view.findViewById(R.id.textError);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        listViewTrends = (RecyclerView)view.findViewById(R.id.listTrends);
        listViewTrends.setLayoutManager(new LinearLayoutManager(getActivity()));

        trendsAdapter = new TrendsAdapter(getActivity());

        listViewTrends.setAdapter(trendsAdapter);
        if (savedInstanceState!=null)
        {
            listTrends=savedInstanceState.getParcelableArrayList(STATE_TRENDS);
            trendsAdapter.setTrendList(listTrends);
        }
        else  {

            listTrends = MyApplication.getWritableDatabase().readTrends(categoryId, limit, region, "");
            trendsAdapter.setTrendList(listTrends);
        }


        if (listTrends.size()==0) {
            sendJsonRequest();
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !areLecturesLoaded) {
            //sendJsonRequest();
            areLecturesLoaded = true;
        }
    }


    @Override
    public void onShowPositive() {



        listTrends = MyApplication.getWritableDatabase().readTrends(categoryId, limit, region, " and trend_difference>0");
        if (listTrends.size()==0) {
            Toast.makeText(getActivity(), "No Positive Trends Found", Toast.LENGTH_SHORT).show();
        } else {
            Collections.sort(listTrends, new Comparator<Trend>() {
                @Override
                public int compare(Trend lhs, Trend rhs) {
                    if (lhs.getTrendDifference() < rhs.getTrendDifference())
                        return 1;
                    else if (lhs.getTrendDifference() > rhs.getTrendDifference())
                        return -1;
                    return 0;
                }
            });
            trendsAdapter.setTrendList(listTrends);
            trendsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onShowNew() {
        listTrends = MyApplication.getWritableDatabase().readTrends(categoryId, limit, region, " and trend_difference<-100");
        if (listTrends.size()==0) {
            Toast.makeText(getActivity(), "No New Entry Found", Toast.LENGTH_SHORT).show();
        } else {
            trendsAdapter.setTrendList(listTrends);
            trendsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onShowAll() {

        trendsAdapter.setTrendList(MyApplication.getWritableDatabase().readTrends(categoryId, limit, region, ""));
        trendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                volleySingleton = VolleySingleton.getmInstance();
                requestQueue = volleySingleton.getRequestQueue();
                MyApplication.getWritableDatabase().deletePosition(categoryId, region);

                sendJsonRequest();

                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 4500);

    }
}
