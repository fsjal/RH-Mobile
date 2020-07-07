package com.project.rhmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.rhmobile.entities.MenuItem;
import com.project.rhmobile.entities.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CALL = 2000;
    private boolean callPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        RecyclerView menuList = findViewById(R.id.menu_list);
        FloatingActionButton callButton = findViewById(R.id.urgent_call_button);

        menuList.setAdapter(new MenuAdapter(this, getMenuItems(), this::onMenuItemClick));
        menuList.setLayoutManager(new LinearLayoutManager(this));
        callButton.setOnClickListener(e -> urgentCall());
    }

    protected void onMenuItemClick(int position, MenuItem item) {
        Intent intent;
        if (item.getServiceType() == ServiceType.Pharmacy) {
            intent = new Intent(this, PharmacyActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("service", item);
        }
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        callPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPermissionGranted = true;
                Log.d("Permission", "2");
                urgentCall();
            }
        }
    }

    protected List<MenuItem> getMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem(ServiceType.Hospital, getString(R.string.find_hospital), R.drawable.ic_hospital,
                getString(R.string.service_hospital), getString(R.string.server_hospital_param)));
        items.add(new MenuItem(ServiceType.Clinic, getString(R.string.find_clinic), R.drawable.ic_clinic,
                getString(R.string.service_clinic), getString(R.string.server_clinic_param)));
        items.add(new MenuItem(ServiceType.Dispensary, getString(R.string.find_dispensary), R.drawable.ic_dispensary,
                getString(R.string.service_dispensary), getString(R.string.server_dispensary_param)));
        items.add(new MenuItem(ServiceType.Pharmacy, getString(R.string.find_pharmacy), R.drawable.ic_pharmacy,
                getString(R.string.service_pharmacy), getString(R.string.server_pharmacy_param)));
        items.add(new MenuItem(ServiceType.Doctor, getString(R.string.find_doctor), R.drawable.ic_doctor,
                getString(R.string.service_doctor), getString(R.string.server_doctor_param)));
        items.add(new MenuItem(ServiceType.Nurse, getString(R.string.find_nurse), R.drawable.ic_nurse,
                getString(R.string.service_nurse), getString(R.string.server_nurse_param)));
        return items;
    }

    private void getCallPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callPermissionGranted = true;
            Log.d("Permission", "1");
            urgentCall();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSIONS_REQUEST_CALL);
        }
    }

    private void urgentCall() {
        if (callPermissionGranted) {
            Log.d("permission", "0");
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(getString(R.string.urgent_call_number)));
            startActivity(intent);
        } else {
            getCallPermission();
        }
    }

    static class MenuAdapter extends RecyclerView.Adapter<MenuHolder> {

        private LayoutInflater inflater;
        private List<MenuItem> items;
        private Listener listener;

        public MenuAdapter(Context context, List<MenuItem> items, Listener listener) {
            this.items = items;
            this.listener = listener;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MenuHolder(inflater.inflate(R.layout.list_menu_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
            holder.bind(position, items.get(position), listener);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        interface Listener {

            void onClickMenuItem(int position, MenuItem item);
        }
    }

    static class MenuHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView contentText;
        private ImageView imageView;

        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            contentText = itemView.findViewById(R.id.list_menu_item_content);
            imageView = itemView.findViewById(R.id.list_menu_item_image);
        }

        public void bind(int position, MenuItem item, MenuAdapter.Listener listener) {
            contentText.setText(item.getContent());
            imageView.setImageResource(item.getImage());
            itemView.setOnClickListener(e -> listener.onClickMenuItem(position, item));
        }
    }
}