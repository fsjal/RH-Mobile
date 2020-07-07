package com.project.rhmobile;

import android.content.Intent;
import android.os.Bundle;

import com.project.rhmobile.entities.MenuItem;
import com.project.rhmobile.entities.ServiceType;

import java.util.ArrayList;
import java.util.List;

public final class PharmacyActivity extends MenuActivity {

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
        items.add(new MenuItem(ServiceType.PharmacyDay, getString(R.string.service_pharmacy_day), R.drawable.ic_pharmacy_day,
                getString(R.string.service_pharmacy_day), getString(R.string.server_pharmacy_day_param)));
        items.add(new MenuItem(ServiceType.PharmacyNight, getString(R.string.service_pharmacy_night), R.drawable.ic_pharmacy_night,
                getString(R.string.service_pharmacy_night), getString(R.string.server_pharmacy_night_param)));
        items.add(new MenuItem(ServiceType.PharmacyGuard, getString(R.string.service_pharmacy_guard), R.drawable.ic_pharmacy_guard,
                getString(R.string.service_pharmacy_guard), getString(R.string.server_pharmacy_guard_param)));
        return items;
    }
}