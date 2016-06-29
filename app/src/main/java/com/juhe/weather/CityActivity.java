package com.juhe.weather;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.juhe.weather.adapter.CityListAdatper;
import com.thinkland.juheapi.common.JsonCallBack;
import com.thinkland.juheapi.data.weather.WeatherData;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

public class CityActivity extends Activity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    private ListView lv_city;
    private ArrayList<String> list;
    private RapidFloatingActionLayout rapid_action_layout;
    private RapidFloatingActionButton activity_main_rfab;
    private RapidFloatingActionHelper fabHelper;
    public static final String CITY_LISTARRAY = "1001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initViews();
        getCities();
        initRapidFloatingActionButton();

    }

    private void initRapidFloatingActionButton() {
        RapidFloatingActionContentLabelList rf = new RapidFloatingActionContentLabelList(this);
        rf.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<RFACLabelItem>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("搜索城市")
                .setResId(R.drawable.search)
                .setIconNormalColor(Color.parseColor("#F8F8FF"))
                .setWrapper(0)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("关于")
                .setResId(R.drawable.about)
                .setIconNormalColor(Color.parseColor("#F8F8FF"))
                .setWrapper(0)
        );
        rf
                .setItems(items)
        ;

        fabHelper = new RapidFloatingActionHelper(this, rapid_action_layout, activity_main_rfab, rf).build();

    }

    private void initViews() {
        activity_main_rfab = (RapidFloatingActionButton) findViewById(R.id.activity_main_rfab);
        rapid_action_layout = (RapidFloatingActionLayout) findViewById(R.id.activity_main_rfal);
        findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        lv_city = (ListView) findViewById(R.id.lv_city);
    }

    private void getCities() {
        WeatherData data = WeatherData.getInstance();
        data.getCities(new JsonCallBack() {

            @Override
            public void jsonLoaded(JSONObject json) {
                // TODO Auto-generated method stub
                try {
                    int code = json.getInt("resultcode");
                    int error_code = json.getInt("error_code");
                    if (error_code == 0 && code == 200) {

                        list = new ArrayList<String>();
                        JSONArray resultArray = json.getJSONArray("result");
                        Set<String> citySet = new HashSet<String>();
                        for (int i = 0; i < resultArray.length(); i++) {
                            String city = resultArray.getJSONObject(i).getString("city");
                            citySet.add(city);
                        }
                        list.addAll(citySet);
                        CityListAdatper adatper = new CityListAdatper(CityActivity.this, list);
                        lv_city.setAdapter(adatper);
                        lv_city.setOnItemClickListener(new OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                // TODO Auto-generated method stub
                                Intent intent = new Intent();
                                intent.putExtra("city", list.get(arg2));
                                setResult(1, intent);
                                finish();
                            }
                        });

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {

    }

    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {
        switch (i) {
            case 0:
                Intent toSearchActivity = new Intent(this, SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(CITY_LISTARRAY, list);
                toSearchActivity.putExtras(bundle);
                startActivity(toSearchActivity);
                finish();
                break;
            case 1:
                showAboutAlertDialog();
                break;
        }
    }

    private void showAboutAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.about_author, null);
        builder.setView(v);
        builder.create();
        builder.show();
    }

}
