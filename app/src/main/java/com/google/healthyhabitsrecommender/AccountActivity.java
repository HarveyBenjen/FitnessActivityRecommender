package com.google.healthyhabitsrecommender;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountActivity extends AppCompatActivity {
    public  static final String EMAIL_KEY = "Email";
    public  static final String PASS_KEY = "Password";
    public  static final String NAME_KEY = "Name";
    public  static final String AGE_KEY = "Age";
    public  static final String SEX_KEY = "Sex";
    public  static final String HEIGHT_KEY = "Height";
    public  static final String WEIGHT_KEY = "Weight";
    public  static final String RADIO_KEY = "radioBtnID";
    public  static final String HEARTRATE_KEY = "HeartRate";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference mDocRef;
    private String currentUser, userEmail,password, name, sex, age, height, weight, heartRate;
    private Button save, logOut, fitbitConnect, preferences;
    private TextView title;
    private EditText nameFiled, ageFiled, heightFiled, weightFiled, heartRateFiled;
    private RadioGroup sexFiled;
    private RadioButton radioBtn;
    private Map<String, Object> dataToSave = new HashMap<>();
    private String clientId = "22B6MH";
    private String clientSecret = "312028d5f13dee85565049c6a93b6471";
    private String authorizationUrl = "https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=22B6MH&redirect_uri=https%3A%2F%2Fcallback&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800";
    private String callbackURL = "https://callback", grantType = "authorization_code";
    private static String code, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Intent receivedIntent = getIntent();
        userEmail = receivedIntent.getStringExtra("userEmail");
        password = receivedIntent.getStringExtra("userPassword");
        db =  FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getUid();
        dataToSave.put(EMAIL_KEY, userEmail);
        dataToSave.put(PASS_KEY, password);
        mDocRef =  db.document("Users/"+currentUser);
        title = findViewById(R.id.emailText);
        title.setText("Welcome back " + userEmail);
        logOut = findViewById(R.id.btnLogout);
        fitbitConnect = findViewById(R.id.btnFitbit);
        preferences = findViewById(R.id.btnPreferences);
        nameFiled = findViewById((R.id.textName));
        ageFiled = findViewById((R.id.textAge));
        sexFiled = findViewById(R.id.radioSex);
        heightFiled = findViewById((R.id.textHeight));
        weightFiled = findViewById((R.id.textWeight));
        heartRateFiled = findViewById(R.id.textHeartRate);
        save = findViewById(R.id.btnSave);

        mDocRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.contains(EMAIL_KEY)) {
                title.setText("Welcome back " + documentSnapshot.getString(EMAIL_KEY));
                userEmail = documentSnapshot.getString(EMAIL_KEY);
            }
            if (documentSnapshot.contains(PASS_KEY)) {
                password = documentSnapshot.getString(PASS_KEY);
            }
            if (documentSnapshot.contains(NAME_KEY)) {
                nameFiled.setText(documentSnapshot.getString(NAME_KEY));
            }
            if (documentSnapshot.contains(RADIO_KEY)) {
                sexFiled.check(Integer.parseInt(documentSnapshot.get(RADIO_KEY).toString()));
            }
            if (documentSnapshot.contains(AGE_KEY)) {
                ageFiled.setText(documentSnapshot.get(AGE_KEY).toString());
            }
            if (documentSnapshot.contains(HEIGHT_KEY)) {
                heightFiled.setText(documentSnapshot.get(HEIGHT_KEY).toString());
            }
            if (documentSnapshot.contains(WEIGHT_KEY)) {
                weightFiled.setText(documentSnapshot.get(WEIGHT_KEY).toString());
            }
            if (documentSnapshot.contains(HEARTRATE_KEY)){
                heartRateFiled.setText(documentSnapshot.get(HEARTRATE_KEY).toString());
            }
        });

        save.setOnClickListener(view -> {
            name = nameFiled.getText().toString().trim();
            age = ageFiled.getText().toString().trim();
            height = heightFiled.getText().toString().trim();
            weight = weightFiled.getText().toString().trim();
            heartRate = heartRateFiled.getText().toString().trim();
            if(!TextUtils.equals(name, "") && !TextUtils.equals(age, "")
                    && !TextUtils.equals(height, "") && !TextUtils.equals(weight, "")
                    && sexFiled.getCheckedRadioButtonId() != -1
                    && !TextUtils.equals(heartRate, "")) {
                radioBtn = findViewById(sexFiled.getCheckedRadioButtonId());
                sex = radioBtn.getText().toString().trim();
                if (Integer.parseInt(age) < 4 || Integer.parseInt(age) > 125 ) {
                    toastMessage("Please re-enter an appropriate age");
                }
                else if (Integer.parseInt(height) < 55 || Integer.parseInt(height) > 260) {
                    toastMessage("Please re-enter an appropriate height");
                }
                else if (Integer.parseInt(weight) < 10 || Integer.parseInt(height) > 600) {
                    toastMessage("Please re-enter an appropriate weight");
                }
                else if (Integer.parseInt(heartRate) < 25 || Integer.parseInt(heartRate) > 100) {
                    toastMessage("Please re-enter an appropriate heart rate");
                }
                else {
                    dataToSave.put(NAME_KEY, name);
                    dataToSave.put(AGE_KEY, age);
                    dataToSave.put(HEIGHT_KEY, height);
                    dataToSave.put(WEIGHT_KEY, weight);
                    dataToSave.put(HEARTRATE_KEY, heartRate);
                    dataToSave.put(RADIO_KEY, sexFiled.getCheckedRadioButtonId());
                    dataToSave.put(SEX_KEY, sex);
                    dataToSave.put(EMAIL_KEY, userEmail);
                    dataToSave.put(PASS_KEY, password);
                    mDocRef.set(dataToSave).addOnSuccessListener(aVoid -> toastMessage("Successfully saved data"));
                }
            }
            else {
                toastMessage("One or more fields are missing");
            }
        });

        logOut.setOnClickListener(view -> {
            mAuth.signOut();
            toastMessage("Successfully logged out");
            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            ///////////////// NEED TO REVOKE THE USERS ACCESS TOKEN WHEN LOGGING OUT
        });

        fitbitConnect.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorizationUrl));
            startActivity(intent);
        });

        preferences.setOnClickListener(view -> {
            Intent intent = new Intent(AccountActivity.this, PreferenceActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(callbackURL)) {
            code = uri.getQueryParameter("code");
            Log.d("code", code);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://www.fitbit.com/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            FitbitClient client = retrofit.create(FitbitClient.class);
            Call<UserFitbit> accessTokenCall = client.getAccessToken(
                    clientId,
                    grantType,
                    callbackURL,
                    code
            );

            accessTokenCall.enqueue(new Callback<UserFitbit>() {
                @Override
                public void onResponse(Call<UserFitbit> call, Response<UserFitbit> response) {
                    token = response.body().getAccessToken();
                }
                @Override
                public void onFailure(Call<UserFitbit> call, Throwable t) {
                }
            });

            Call<ResponseBody> responseBodyCall = client.getResponse("Bearer " + token);
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()) {
                        String json = response.body().toString();
                        toastMessage("Successfully retrieved Fitbit Data" + "\nTesting: " + json);
                    }
                    else {
                        toastMessage("Successfully connected to Fitbit");
                        fitbitConnect.setText("Press again to retrieve data!");
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        }
    }

    private void toastMessage (String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
