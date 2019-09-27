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

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailAddress, password;
    private String email, pass;
    private Button logIn, signUp, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent receivedIntent = getIntent();
        email = receivedIntent.getStringExtra("userEmail");
        pass = receivedIntent.getStringExtra("userPassword");
        mAuth = FirebaseAuth.getInstance();
        emailAddress = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        emailAddress.setText(email);
        password.setText(pass);
        logIn = findViewById(R.id.btnLogIn);
        signUp = findViewById(R.id.btnSignUp);
        about = findViewById(R.id.btnAbout);

        logIn.setOnClickListener(view -> {
            email = emailAddress.getText().toString();
            pass = password.getText().toString();
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        toastMessage("Successfully logged in");
                        Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                        intent.putExtra("userEmail", email);
                        intent.putExtra("userPassword", pass);
                        startActivity(intent);
                    }
                    else {
                        toastMessage("Either not a valid email/password or no internet connection");
                    }
                });
            }
            else {
                toastMessage("You forgot to include an email and/or password");
            }
        });

        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        about.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void toastMessage (String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
