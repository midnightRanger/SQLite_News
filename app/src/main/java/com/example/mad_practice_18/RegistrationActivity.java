package com.example.mad_practice_18;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    DatabaseHelper database;
    CheckBox isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        database = new DatabaseHelper(this);
        Button register = findViewById(R.id.register_btn);
        isAdmin = findViewById(R.id.admin_cb);
        register.setOnClickListener(v -> register());
    }

    private void register() {
        EditText login = findViewById(R.id.login_et);
        EditText password = findViewById(R.id.password_et);
        CheckBox isAdmin = findViewById(R.id.admin_cb);

        String l = login.getText().toString().trim();
        String p = password.getText().toString().trim();
        boolean a = isAdmin.isChecked();
        if(l.isEmpty() || p.isEmpty()){
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }
        database.addUser(l, p, a);

        Intent intent = new Intent(this, AdminNewsActivity.class);
        startActivity(intent);
        finish();
    }
}