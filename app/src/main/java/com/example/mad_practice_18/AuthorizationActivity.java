package com.example.mad_practice_18;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class AuthorizationActivity extends AppCompatActivity {
    DatabaseHelper database;
    Button authorize;
    UserModel authorizingUser;

    private Executor executor;
    private BiometricPrompt biometric;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        database = new DatabaseHelper(this);
        authorize = findViewById(R.id.admin_cb);
        authorize.setOnClickListener(v -> authorize());

        executor = ContextCompat.getMainExecutor(this);
        biometric = new BiometricPrompt(AuthorizationActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e("Auth", errString.toString());
                Toast.makeText(AuthorizationActivity.this,
                        "Ошибка. Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.w("Auth", "Authentication successful");
                Toast.makeText(AuthorizationActivity.this,
                        "Авторизация пройдена", Toast.LENGTH_SHORT).show();

                Intent adminNewsActivity = new Intent(AuthorizationActivity.this, AdminNewsActivity.class);
                adminNewsActivity.putExtra("authorized", authorizingUser);
                startActivity(adminNewsActivity);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e("Auth", "Authentication failed");
                Toast.makeText(AuthorizationActivity.this,
                        "Ошибка. Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Авторизация")
                .setSubtitle("Подтвердите, что являетесь администратором")
                .setNegativeButtonText("Отмена")
                .build();
    }

    private void authorize() {
        EditText login, password;
        login = findViewById(R.id.login_et);
        password = findViewById(R.id.password_et);

        String l = login.getText().toString().trim();
        String p = password.getText().toString();
        authorizingUser = database.findUser(l, p);
        if (authorizingUser == null) {
            Toast.makeText(this, "Пользователя с такими данными нет в базе данных",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (authorizingUser.IsAdmin) {
            //check with biometric
            biometric.authenticate(promptInfo);
        } else {
            Intent newsActivity = new Intent(this, ReaderNewsActivity.class);
            startActivity(newsActivity);
        }
    }
}