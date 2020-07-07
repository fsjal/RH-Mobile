package com.project.rhmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public final class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailText;
    private TextInputEditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button connect = findViewById(R.id.connect_button);
        TextView register = findViewById(R.id.register_text);
        emailText = findViewById(R.id.email_text);
        passwordText = findViewById(R.id.password_text);

        connect.setOnClickListener(e -> onConnect());
        register.setOnClickListener(e -> onRegister());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Volley.newRequestQueue(this).cancelAll(request -> true);
    }

    private void onRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void onConnect() {
        if (checkFields()) processRequest();
    }

    private void processRequest() {
        String url = getString(R.string.host) + getString(R.string.server_login);
        User user = new User(emailText.getText().toString(), passwordText.getText().toString());
        Users.get(this, url, user, response -> onResponse(response.trim()), this::onError);
    }

    private void onResponse(String response) {
        Log.d("toast", response);
        if (!response.contains("false")) {
            String[] results = response.split(";");
            User user = new User(Integer.parseInt(results[0]), results[1], results[2], results[3], results[4], results[5]);
            Toast.makeText(this, getString(R.string.login_success) + getText(R.string.welcome) + user.getLastName(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_LONG).show();
        }
    }

    private void onError(VolleyError error) {
        Log.e("toast", error.getMessage());
    }

    private boolean checkFields() {
        return  checkField(emailText, R.string.empty_email) &&
                checkField(passwordText, R.string.empty_password);
    }

    private boolean checkField(TextInputEditText field, int idStr) {
        if (field.getText() != null && field.getText().toString().isEmpty()) {
            field.setError(getString(idStr));
            return false;
        }
        return true;
    }
}