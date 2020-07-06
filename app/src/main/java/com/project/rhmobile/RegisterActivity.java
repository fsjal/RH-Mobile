package com.project.rhmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.project.rhmobile.dao.Users;
import com.project.rhmobile.entities.User;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText nameText;
    private TextInputEditText prenameText;
    private TextInputEditText emailText;
    private TextInputEditText passwordText;
    private TextInputEditText phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button confirm = findViewById(R.id.confirm_button);
        TextView reset = findViewById(R.id.reset_text);
        nameText = findViewById(R.id.name_text);
        prenameText = findViewById(R.id.prename_text);
        emailText = findViewById(R.id.email_text);
        passwordText = findViewById(R.id.password_text);
        phoneText = findViewById(R.id.phone_text);
        
        confirm.setOnClickListener(e -> onConfirm());
        reset.setOnClickListener(e -> onReset());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Volley.newRequestQueue(this).cancelAll(request -> true);
    }

    private void onConfirm() {
        if (checkFields()) processRequest();
    }

    private void processRequest() {
        String url = getString(R.string.host) + getString(R.string.server_register);
        User user = new User(
                0,
                nameText.getText().toString(),
                prenameText.getText().toString(),
                emailText.getText().toString(),
                passwordText.getText().toString(),
                phoneText.getText().toString());

        Users.add(this, url, user, response -> onResponse(response.trim()), this::onError);
    }

    private void onResponse(String response) {
        Log.d("toast", response);
        if (response.equals("true")) {
            Toast.makeText(this, R.string.user_added, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Toast.makeText(this, R.string.error_adding_user, Toast.LENGTH_LONG).show();
    }

    private void onError(VolleyError error) {
        Log.e("toast", error.getMessage());
    }

    private boolean checkFields() {
        return  checkField(nameText, R.string.empty_name) &&
                checkField(prenameText, R.string.empty_prename) &&
                checkField(emailText, R.string.empty_email) &&
                checkField(passwordText, R.string.empty_password) &&
                checkField(phoneText, R.string.empty_phone);
    }

    private boolean checkField(TextInputEditText field, int idStr) {
        if (field.getText() != null && field.getText().toString().isEmpty()) {
            field.setError(getString(idStr));
            return false;
        }
        return true;
    }

    private void onReset() {
        nameText.setText("");
        prenameText.setText("");
        emailText.setText("");
        passwordText.setText("");
        phoneText.setText("");
    }
}