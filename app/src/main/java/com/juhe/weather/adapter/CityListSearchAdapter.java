package com.juhe.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.juhe.weather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjing on 2016/5/17.
 */
public class CityListSearchAdapter extends BaseAdapter {

    private List<String> cityList;
    private ViewHolder holder;
    private Context context;

    public CityListSearchAdapter(ArrayList<String> CityList, Context context) {
        this.cityList = CityList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.citylist_search, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.tv_cityList);
            convertView.setTag(holder);
        } else {
            convertView.getTag();
        }
        if (cityList.size() != 0){
            holder.text.setText(cityList.get(position));
        }

        return convertView;
    }

    class ViewHolder {
        TextView text;
    }

}
