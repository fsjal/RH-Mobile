package com.project.rhmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
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
    private Request<String> requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
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
        if (requestQueue != null) requestQueue.cancel();
    }

    private void onConfirm() {
        if (checkFields()) {
            processRequest();
        }
    }

    private void processRequest() {
        String url = getString(R.string.host) + getString(R.string.server_register);
        User user = new User(
                nameText.getText().toString(),
                prenameText.getText().toString(),
                emailText.getText().toString(),
                passwordText.getText().toString(),
                phoneText.getText().toString());

        Users.add(this, url, user, response -> Log.d("toast", response),
                error -> Log.e("toast", error.getMessage()));
    }

    private boolean checkFields() {
        return  checkField(nameText, R.string.empty_name) &&
                checkField(prenameText, R.string.empty_prename) &&
                checkField(emailText, R.string.empty_email) &&
                checkField(passwordText, R.string.empty_password);

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