package com.google.healthyhabitsrecommender;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreferenceActivity extends AppCompatActivity {
    public static final String BTN_1 = "btn1", BTN_2 = "btn2", BTN_3 = "btn3", BTN_4 = "btn4",
        BTN_5 = "btn5";
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private DocumentReference mDocRef;
    private AutoCompleteTextView prefer1, prefer2, prefer3, prefer4, prefer5;
    private ArrayAdapter<String> myAdapter;
    private Button save, recommend, home;
    private RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5;
    private RadioButton radioBtn;
    private String currentUser, activity1, activity2, activity3, activity4, activity5;
    private String rating1, rating2, rating3, rating4, rating5, key1, key2, key3, key4, key5;
    private Map<String, Object> dataToSave = new HashMap<>(), buttonsToSave = new HashMap<>(),
            keysToSave = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        db =  FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getUid();

        save = findViewById(R.id.btnSave);
        recommend = findViewById(R.id.btnRecommendations);
        home = findViewById(R.id.btnHome);
        prefer1= findViewById(R.id.text1);
        prefer2 = findViewById(R.id.box2);
        prefer3 = findViewById(R.id.box3);
        prefer4 = findViewById(R.id.box4);
        prefer5 = findViewById(R.id.box5);
        radioGroup1= findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
        radioGroup4 = findViewById(R.id.radioGroup4);
        radioGroup5 = findViewById(R.id.radioGroup5);

        myAdapter = new ArrayAdapter<>(PreferenceActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Activities));
        myAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        prefer1.setAdapter(myAdapter);
        prefer2.setAdapter(myAdapter);
        prefer3.setAdapter(myAdapter);
        prefer4.setAdapter(myAdapter);
        prefer5.setAdapter(myAdapter);

        mDocRef =  db.document("Ratings2/"+currentUser);
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                List<String> keys = new ArrayList<>();
                Map<String, Object> map;
                if (document.exists()) {
                     map = document.getData();
                    if (map != null) {
                        for (String key : map.keySet()) {
                            keys.add(key);
                        }
                    }
                    prefer1.setText(keys.get(0));
                    prefer2.setText(keys.get(1));
                    prefer3.setText(keys.get(2));
                    prefer4.setText(keys.get(3));
                    prefer5.setText(keys.get(4));
                }
            }
        });

        mDocRef =  db.document("Ratings2/"+currentUser+"/RadioButtonIDs/btn_IDs");
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()) {
                    radioGroup1.check(Integer.parseInt(document.get(BTN_1).toString()));
                    radioGroup2.check(Integer.parseInt(document.get(BTN_2).toString()));
                    radioGroup3.check(Integer.parseInt(document.get(BTN_3).toString()));
                    radioGroup4.check(Integer.parseInt(document.get(BTN_4).toString()));
                    radioGroup5.check(Integer.parseInt(document.get(BTN_5).toString()));
                }
            }
        });

        save.setOnClickListener(view -> {
            activity1 = prefer1.getText().toString().trim();
            activity2 = prefer2.getText().toString().trim();
            activity3 = prefer3.getText().toString().trim();
            activity4 = prefer4.getText().toString().trim();
            activity5 = prefer5.getText().toString().trim();

            if (!TextUtils.equals(activity1, "") && !TextUtils.equals(activity2, "") &&
                    !TextUtils.equals(activity3, "") && !TextUtils.equals(activity4, "") &&
                    !TextUtils.equals(activity5, "") &&
                    radioGroup1.getCheckedRadioButtonId() != -1 &&
                    radioGroup2.getCheckedRadioButtonId() != -1 &&
                    radioGroup3.getCheckedRadioButtonId() != -1 &&
                    radioGroup4.getCheckedRadioButtonId() != -1 &&
                    radioGroup5.getCheckedRadioButtonId() != -1) {

                db.collection("PhysicalActivities")
                        .whereEqualTo("Name", activity1)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    key1 = currentUser + document.getId();
                                }
                            }
                        });

                db.collection("PhysicalActivities")
                        .whereEqualTo("Name", activity2)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    key2 = currentUser + document.getId();
                                }
                            }
                        });

                db.collection("PhysicalActivities")
                        .whereEqualTo("Name", activity3)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    key3 = currentUser + document.getId();
                                }
                            }
                        });

                db.collection("PhysicalActivities")
                        .whereEqualTo("Name", activity4)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    key4 = currentUser + document.getId();
                                }
                            }
                        });

                db.collection("PhysicalActivities")
                        .whereEqualTo("Name", activity5)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    key5 = currentUser + document.getId();
                                }
                            }
                        });

                radioBtn = findViewById(radioGroup1.getCheckedRadioButtonId());
                buttonsToSave.put(BTN_1, radioBtn.getId());
                rating1 = radioBtn.getText().toString().trim();

                radioBtn = findViewById(radioGroup2.getCheckedRadioButtonId());
                buttonsToSave.put(BTN_2, radioBtn.getId());
                rating2 = radioBtn.getText().toString().trim();

                radioBtn = findViewById(radioGroup3.getCheckedRadioButtonId());
                buttonsToSave.put(BTN_3, radioBtn.getId());
                rating3 = radioBtn.getText().toString().trim();

                radioBtn = findViewById(radioGroup4.getCheckedRadioButtonId());
                buttonsToSave.put(BTN_4, radioBtn.getId());
                rating4 = radioBtn.getText().toString().trim();

                radioBtn = findViewById(radioGroup5.getCheckedRadioButtonId());
                buttonsToSave.put(BTN_5, radioBtn.getId());
                rating5 = radioBtn.getText().toString().trim();
                mDocRef.set(buttonsToSave);

                mDocRef = db.document("Ratings/"+key1);
                keysToSave.put("Rating", rating1);
                mDocRef.set(keysToSave);

                mDocRef = db.document("Ratings/"+key2);
                keysToSave.put("Rating", rating2);
                mDocRef.set(keysToSave);

                mDocRef = db.document("Ratings/"+key3);
                keysToSave.put("Rating", rating3);
                mDocRef.set(keysToSave);

                mDocRef = db.document("Ratings/"+key4);
                keysToSave.put("Rating", rating4);
                mDocRef.set(keysToSave);

                mDocRef = db.document("Ratings/"+key5);
                keysToSave.put("Rating", rating5);
                mDocRef.set(keysToSave);

                mDocRef = db.document("Ratings2/"+currentUser);
                dataToSave.put(activity1, rating1);
                dataToSave.put(activity2, rating2);
                dataToSave.put(activity3, rating3);
                dataToSave.put(activity4, rating4);
                dataToSave.put(activity5, rating5);
                mDocRef.set(dataToSave).addOnSuccessListener(aVoid -> toastMessage("Successfully saved preferences"));
            }
            else {
                toastMessage("Missing activity and/or rating");
            }
        });

        recommend.setOnClickListener(view -> {
            Intent intent = new Intent(PreferenceActivity.this, RecommendationsActivity.class);
            startActivity(intent);
        });

        home.setOnClickListener(view -> {
            Intent intent = new Intent(PreferenceActivity.this, AccountActivity.class);
            startActivity(intent);
        });
    }


    private void toastMessage (String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
