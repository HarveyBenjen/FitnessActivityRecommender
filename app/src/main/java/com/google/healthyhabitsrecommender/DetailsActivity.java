package com.google.healthyhabitsrecommender;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {
    private String activity, duration, energy, heart_rate, intensity;
    private TextView titleText, durationText, energyText, intensityText, heart_rateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent receivedIntent = getIntent();
        activity = receivedIntent.getStringExtra("activity");
        duration = receivedIntent.getStringExtra("duration");
        energy = receivedIntent.getStringExtra("energy");
        heart_rate = receivedIntent.getStringExtra("heart_rate");
        intensity = receivedIntent.getStringExtra("intensity");

        titleText = findViewById(R.id.textActivity);
        durationText = findViewById(R.id.textDuration);
        energyText = findViewById(R.id.textEnergy);
        heart_rateText = findViewById(R.id.textHeartRate);
        intensityText = findViewById(R.id.textIntensity);

        if(!TextUtils.isEmpty(activity)) {
            titleText.setText(activity);
        }
        if(!TextUtils.isEmpty(duration)) {
            durationText.setText(duration + "  minutes");
        }
        if(!TextUtils.isEmpty(energy)) {
            energyText.setText(energy + "  Kcal");
        }
        if(!TextUtils.isEmpty(heart_rate)) {
            heart_rateText.setText(heart_rate + "  bpm");
        }
        if(!TextUtils.isEmpty(intensity)) {
            intensityText.setText(intensity);
        }
    }

    private void toastMessage (String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
