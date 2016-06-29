package com.juhe.weather;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.juhe.weather.adapter.CityListAdatper;
import com.juhe.weather.adapter.CityListSearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {

    private ArrayList<String> cityList;
    private SearchView sv_city;
    private FrameLayout fl_prompt;
    private Bundle b;
    private ArrayList<String> resultList;
    private Fragment fragment;
    private FragmentManager fm;
    public static final String RESULT_LIST = "100001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        fm = getFragmentManager();
        b = getIntent().getExtras();
        if (b != null) {
            cityList = b.getStringArrayList(CityActivity.CITY_LISTARRAY);
        }
        initView();
        initSearchOfCity();
    }

    private void initSearchOfCity() {
        sv_city.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals("")) {
                    switchResultFragment(newText);
                } else {
                }
                return true;
            }

            private void switchResultFragment(final String newText) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < cityList.size(); i++) {
                            if (cityList.get(i).equals(newText)) {
                                resultList.add(newText);
                                break;
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fragment = new searchFragment();
                                b = new Bundle();
                                b.putStringArrayList(RESULT_LIST, resultList);
                                fragment.setArguments(b);
                                fm.beginTransaction().replace(R.id.fl_prompt, fragment).commit();
                            }
                        });

                    }
                }).start();

            }
        });
    }

    private void initView() {
        fl_prompt = (FrameLayout) findViewById(R.id.fl_prompt);
        sv_city = (SearchView) findViewById(R.id.sv_city);
        resultList = new ArrayList<>();
    }
    private void finishActivity(){

    }
}
