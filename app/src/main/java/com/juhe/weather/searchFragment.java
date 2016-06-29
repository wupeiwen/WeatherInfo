package com.juhe.weather;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.juhe.weather.adapter.CityListSearchAdapter;
import com.juhe.weather.service.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by anjing on 2016/5/17.
 */
public class searchFragment extends Fragment {
    private View view;
    private ListView lv_promit;
    private Bundle fromSearchActivityBundle;
    private ArrayList<String> resultList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, null);
        fromSearchActivityBundle = getArguments();
        if (fromSearchActivityBundle != null) {
            resultList = fromSearchActivityBundle.getStringArrayList(SearchActivity.RESULT_LIST);
        }
        lv_promit = (ListView) view.findViewById(R.id.lv_promit);
        lv_promit.setAdapter(new CityListSearchAdapter(resultList, getActivity()));
        lv_promit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = resultList.get(position);
//                Intent i = new Intent(getActivity(), WeatherActivity.class);
//                i.putExtra("city", city);
//                startActivity(i);
                EventBus.getDefault().post(new MessageEvent(city));
                getActivity().finish();
            }
        });

        return view;
    }
}
