package com.google.healthyhabitsrecommender;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailFiled, passwordFiled, checkPassword;
    private String email, password;
    private Button register, loginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailFiled = findViewById(R.id.textEmail);
        passwordFiled = findViewById(R.id.textPassword);
        checkPassword = findViewById(R.id.textPassVerify);
        register = findViewById(R.id.btnRegister);
        loginPage = findViewById(R.id.btnAccount);
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(view -> {
            email = emailFiled.getText().toString();
            password = passwordFiled.getText().toString();
            String passVerify = checkPassword.getText().toString();
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(passVerify)) {
                if (password.equals(passVerify)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            toastMessage("Successfully created account");
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            intent.putExtra("userEmail", email);
                            intent.putExtra("userPassword", password);
                            startActivity(intent);
                        }
                        else {
                            toastMessage("Either email is not valid, is already registered, or no internet connection");
                        }
                    });
                }
                else {
                    toastMessage("Passwords do not match");
                }
            }
            else {
                toastMessage("You forgot to include an email and/or password");
            }
        });

        loginPage.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void toastMessage (String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
