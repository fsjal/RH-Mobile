package com.project.rhmobile;

import android.content.Intent;
import android.os.Bundle;

import com.project.rhmobile.entities.MenuItem;
import com.project.rhmobile.entities.Service;

import java.util.ArrayList;
import java.util.List;

public class PharmacyActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onMenuItemClick(int position, MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("service", item);
        startActivity(intent);
    }

    @Override
    protected List<MenuItem> getMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem(Service.PharmacyDay, getString(R.string.find_pharmacy_day), R.drawable.ic_pharmacy_day));
        items.add(new MenuItem(Service.PharmacyNight, getString(R.string.find_pharmacy_night), R.drawable.ic_pharmacy_night));
        items.add(new MenuItem(Service.PharmacyGuard, getString(R.string.find_pharmacy_guard), R.drawable.ic_pharmacy_guard));
        return items;
    }
}