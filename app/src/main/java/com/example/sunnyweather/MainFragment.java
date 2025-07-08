package com.example.sunnyweather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sunnyweather.entity.City;
import com.example.sunnyweather.entity.County;
import com.example.sunnyweather.entity.Province;
import com.example.sunnyweather.util.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final int PROVINCE_LEVEL = 1;
    private static final int CITY_LEVEL = 2;
    private static final int COUNTY_LEVEL = 3;
    private ImageView back;
    private TextView title;
    private ListView listView;
    private List<String> names = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private List<County> counties = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private Province selectedProvince;
    private int level;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.main_frag, container, false);
        back = (ImageView) view.findViewById(R.id.back_btn);
        title = ((TextView) view.findViewById(R.id.title));
        listView = ((ListView) view.findViewById(R.id.list_view));
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        back.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showProvinces();
    }

    private void showProvinces() {
        // 省的信息只需获取一次
        if (!provinces.isEmpty()) {
            level = PROVINCE_LEVEL;
            back.setVisibility(View.GONE);
            title.setText("中国");
            names.clear();
            for (Province province : provinces) {
                names.add(province.getName());
            }
            adapter.notifyDataSetChanged();
            return;
        }
        showProgress();
        Thread async = new Thread(() -> {
            provinces.addAll(Utilities.getProvinces());
            requireActivity().runOnUiThread(() -> {
                closeProgress();
                if (provinces.isEmpty()) {
                    Toast.makeText(getContext(), "load fail", Toast.LENGTH_SHORT).show();
                } else {
                    back.setVisibility(View.GONE);
                    title.setText("中国");
                    level = PROVINCE_LEVEL;
                    showProvinces();
                }
            });
        });
        async.start();
    }
    private void showCities(Province province) {
        showProgress();
        Thread async = new Thread(() -> {
            cities.clear();
            cities.addAll(Utilities.getCities(province));
            requireActivity().runOnUiThread(() -> {
                closeProgress();
                if (provinces.isEmpty()) {
                    Toast.makeText(getContext(), "load fail", Toast.LENGTH_SHORT).show();
                } else {
                    level = CITY_LEVEL;
                    back.setVisibility(View.VISIBLE);
                    title.setText(province.getName());
                    selectedProvince = province;

                    names.clear();
                    for (City city : cities) {
                        names.add(city.getName());
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        });
        async.start();
    }
    private void showCounties(City city) {
        showProgress();
        Thread async = new Thread(() -> {
            counties.clear();
            counties.addAll(Utilities.getCounties(selectedProvince, city));
            requireActivity().runOnUiThread(() -> {
                closeProgress();
                if (counties.isEmpty()) {
                    Toast.makeText(getContext(), "load fail", Toast.LENGTH_SHORT).show();
                } else {
                    level = COUNTY_LEVEL;
                    title.setText(city.getName());
                    names.clear();
                    for (County county : counties) {
                        names.add(county.getName());
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        });
        async.start();
    }

    private void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("loading....");
        }
        progressDialog.show();
    }

    private void closeProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (level) {
            case PROVINCE_LEVEL:
                showCities(provinces.get(position));
                break;
            case CITY_LEVEL:
                showCounties(cities.get(position));
                break;
            case COUNTY_LEVEL:
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == back.getId()) {
            if (level == CITY_LEVEL) {
                showProvinces();
            } else if (level == COUNTY_LEVEL) {
                showCities(selectedProvince);
            }
        }
    }
}
