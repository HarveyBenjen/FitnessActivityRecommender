package com.google.healthyhabitsrecommender;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;

public class RecommendationsActivity extends AppCompatActivity {
    public  static final String ACTIVITY1_KEY = "phy_act1", ACTIVITY2_KEY = "phy_act2",
            ACTIVITY3_KEY = "phy_act3",ACTIVITY4_KEY = "phy_act4", ACTIVITY5_KEY = "phy_act5",
            DURATION1_KEY = "duration1", DURATION2_KEY = "duration2", DURATION3_KEY = "duration3",
            DURATION4_KEY = "duration4", DURATION5_KEY = "duration5", ENERGY1_KEY = "energy1",
            ENERGY2_KEY = "energy2", ENERGY3_KEY = "energy3", ENERGY4_KEY = "energy4",
            ENERGY5_KEY = "energy5", HEARTRATE1_KEY = "heart_rate1", HEARTRATE2_KEY = "heart_rate2",
            HEARTRATE3_KEY = "heart_rate3", HEARTRATE4_KEY = "heart_rate4",
            HEARTRATE5_KEY = "heart_rate5", INTENSITY1_KEY = "intensity1",
            INTENSITY2_KEY = "intensity2", INTENSITY3_KEY = "intensity3",
            INTENSITY4_KEY = "intensity4", INTENSITY5_KEY = "intensity5";
    private Button generateRecom, act1, act2, act3, act4, act5, home;
    private TextView energy1, energy2, energy3, energy4, energy5;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference mDocRef;
    private String currentUser, activity1, activity2, activity3, activity4, activity5,
            duration1, duration2, duration3, duration4, duration5,
            energySpent1, energySpent2,energySpent3, energySpent4, energySpent5,
            heart_rate1, heart_rate2, heart_rate3, heart_rate4, heart_rate5,
            intensity1, intensity2, intensity3, intensity4, intensity5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getUid();
        mDocRef = db.document("Recommendations/" + currentUser);

        generateRecom = findViewById(R.id.btnGenerate);
        home = findViewById(R.id.btnHome);
        act1 = findViewById(R.id.activity1);
        act2 = findViewById(R.id.activity2);
        act3 = findViewById(R.id.activity3);
        act4 = findViewById(R.id.activity4);
        act5 = findViewById(R.id.activity5);
        energy1 = findViewById(R.id.text1);
        energy2 = findViewById(R.id.text2);
        energy3 = findViewById(R.id.text3);
        energy4 = findViewById(R.id.text4);
        energy5 = findViewById(R.id.text5);

        mDocRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.contains(ACTIVITY1_KEY) && documentSnapshot.contains(DURATION1_KEY) &&
                    documentSnapshot.contains(ENERGY1_KEY) && documentSnapshot.contains(HEARTRATE1_KEY) &&
                    documentSnapshot.contains(INTENSITY1_KEY)) {
                act1.setText(documentSnapshot.getString(ACTIVITY1_KEY));
                energy1.setText(documentSnapshot.getString(ENERGY1_KEY) + " Kcal");
                activity1 = documentSnapshot.getString(ACTIVITY1_KEY);
                duration1 = documentSnapshot.getString(DURATION1_KEY);
                energySpent1 = documentSnapshot.getString(ENERGY1_KEY);
                heart_rate1 = documentSnapshot.getString(HEARTRATE1_KEY);
                intensity1 = documentSnapshot.getString(INTENSITY1_KEY);
            }
            if (documentSnapshot.contains(ACTIVITY2_KEY) && documentSnapshot.contains(DURATION2_KEY) &&
                    documentSnapshot.contains(ENERGY2_KEY) && documentSnapshot.contains(HEARTRATE2_KEY) &&
                    documentSnapshot.contains(INTENSITY2_KEY)) {
                act2.setText(documentSnapshot.getString(ACTIVITY2_KEY));
                energy2.setText(documentSnapshot.getString(ENERGY2_KEY) + " Kcal");
                activity2 = documentSnapshot.getString(ACTIVITY2_KEY);
                duration2 = documentSnapshot.getString(DURATION2_KEY);
                energySpent2 = documentSnapshot.getString(ENERGY2_KEY);
                heart_rate2 = documentSnapshot.getString(HEARTRATE2_KEY);
                intensity2 = documentSnapshot.getString(INTENSITY2_KEY);
            }
            if (documentSnapshot.contains(ACTIVITY3_KEY) && documentSnapshot.contains(DURATION3_KEY) &&
                    documentSnapshot.contains(ENERGY3_KEY) && documentSnapshot.contains(HEARTRATE3_KEY) &&
                    documentSnapshot.contains(INTENSITY3_KEY)) {
                act3.setText(documentSnapshot.getString(ACTIVITY3_KEY));
                energy3.setText(documentSnapshot.getString(ENERGY3_KEY) + " Kcal");
                activity3 = documentSnapshot.getString(ACTIVITY3_KEY);
                duration3 = documentSnapshot.getString(DURATION3_KEY);
                energySpent3 = documentSnapshot.getString(ENERGY3_KEY);
                heart_rate3 = documentSnapshot.getString(HEARTRATE3_KEY);
                intensity3 = documentSnapshot.getString(INTENSITY3_KEY);
            }
            if (documentSnapshot.contains(ACTIVITY4_KEY) && documentSnapshot.contains(DURATION4_KEY) &&
                    documentSnapshot.contains(ENERGY4_KEY) && documentSnapshot.contains(HEARTRATE4_KEY) &&
                    documentSnapshot.contains(INTENSITY4_KEY)) {
                act4.setText(documentSnapshot.getString(ACTIVITY4_KEY));
                energy4.setText(documentSnapshot.getString(ENERGY4_KEY) + " Kcal");
                activity4 = documentSnapshot.getString(ACTIVITY4_KEY);
                duration4 = documentSnapshot.getString(DURATION4_KEY);
                energySpent4 = documentSnapshot.getString(ENERGY4_KEY);
                heart_rate4 = documentSnapshot.getString(HEARTRATE4_KEY);
                intensity4 = documentSnapshot.getString(INTENSITY4_KEY);
            }
            if (documentSnapshot.contains(ACTIVITY5_KEY) && documentSnapshot.contains(DURATION5_KEY) &&
                    documentSnapshot.contains(ENERGY5_KEY) && documentSnapshot.contains(HEARTRATE5_KEY) &&
                    documentSnapshot.contains(INTENSITY5_KEY)) {
                act5.setText(documentSnapshot.getString(ACTIVITY5_KEY));
                energy5.setText(documentSnapshot.getString(ENERGY5_KEY) + " Kcal");
                activity5 = documentSnapshot.getString(ACTIVITY5_KEY);
                duration5 = documentSnapshot.getString(DURATION5_KEY);
                energySpent5 = documentSnapshot.getString(ENERGY5_KEY);
                heart_rate5 = documentSnapshot.getString(HEARTRATE5_KEY);
                intensity5 = documentSnapshot.getString(INTENSITY5_KEY);
            }
        });

        act1.setOnClickListener(view -> {
            Intent intent = new Intent(RecommendationsActivity.this, DetailsActivity.class);
            intent.putExtra("activity", activity1);
            intent.putExtra("duration", duration1);
            intent.putExtra("energy", energySpent1);
            intent.putExtra("heart_rate", heart_rate1);
            intent.putExtra("intensity", intensity1);
            startActivity(intent);
        });

        act2.setOnClickListener(view -> {
            Intent intent = new Intent(RecommendationsActivity.this, DetailsActivity.class);
            intent.putExtra("activity", activity2);
            intent.putExtra("duration", duration2);
            intent.putExtra("energy", energySpent2);
            intent.putExtra("heart_rate", heart_rate2);
            intent.putExtra("intensity", intensity2);
            startActivity(intent);
        });

        act3.setOnClickListener(view -> {
            Intent intent = new Intent(RecommendationsActivity.this, DetailsActivity.class);
            intent.putExtra("activity", activity3);
            intent.putExtra("duration", duration3);
            intent.putExtra("energy", energySpent3);
            intent.putExtra("heart_rate", heart_rate3);
            intent.putExtra("intensity", intensity3);
            startActivity(intent);
        });

        act4.setOnClickListener(view -> {
            Intent intent = new Intent(RecommendationsActivity.this, DetailsActivity.class);
            intent.putExtra("activity", activity4);
            intent.putExtra("duration", duration4);
            intent.putExtra("energy", energySpent4);
            intent.putExtra("heart_rate", heart_rate4);
            intent.putExtra("intensity", intensity4);
            startActivity(intent);
        });

        act5.setOnClickListener(view -> {
            Intent intent = new Intent(RecommendationsActivity.this, DetailsActivity.class);
            intent.putExtra("activity", activity5);
            intent.putExtra("duration", duration5);
            intent.putExtra("energy", energySpent5);
            intent.putExtra("heart_rate", heart_rate5);
            intent.putExtra("intensity", intensity5);
            startActivity(intent);
        });

        home.setOnClickListener(view -> {
            Intent intent = new Intent(RecommendationsActivity.this, AccountActivity.class);
            startActivity(intent);
        });

        generateRecom.setOnClickListener(view -> {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n\t\"user\":{\n\t\t\"user\": \"nhLgzAckDzan7YgNoCLgMyztAqE2\"\n\t}\n}");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/receive-recommendations")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "PostmanRuntime/7.15.2")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Postman-Token", "7da83fcd-c3ca-44ca-8647-fffedf2448bc,2e776d2d-94f3-4faf-a8e7-33b5f61fe9ca")
                    .addHeader("Host", "localhost:8080")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Content-Length", "57")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("cache-control", "no-cache")
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void toastMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
