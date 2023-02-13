package kr.ac.mjc.damsan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PersonActivity extends AppCompatActivity {

    private int count1 = 0;
    private int count2 = 0;
    private int count3 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        TextView adultTv = findViewById(R.id.adult_et);
        TextView childrenTv = findViewById(R.id.children_et);
        TextView babyTv = findViewById(R.id.baby_et);

        Button plus1Btn = findViewById(R.id.plus1_btn);
        Button plus2Btn = findViewById(R.id.plus2_btn);
        Button plus3Btn = findViewById(R.id.plus3_btn);
        Button minus1Btn = findViewById(R.id.minus1_btn);
        Button minus2Btn = findViewById(R.id.minus2_btn);
        Button minus3Btn = findViewById(R.id.minus3_btn);
        Button finishBtn = findViewById(R.id.finish_btn);

        plus1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1++;
                adultTv.setText("어른                       " + count1 + "" + "명");
            }
        });

        plus2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count2++;
                childrenTv.setText("어린이                   " + count2 + "" + "명");
            }
        });

        plus3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count3++;
                babyTv.setText("유아                       " + count3 + "" + "명");
            }
        });

        minus1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1--;
                adultTv.setText("어른                       " + count1 + "" + "명");
            }
        });

        minus2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count2--;
                childrenTv.setText("어린이                   " + count2 + "" + "명");
            }
        });

        minus3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count3--;
                babyTv.setText("유아                       " + count3 + "" + "명");
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adult = adultTv.getText().toString();
                String children = childrenTv.getText().toString();
                String baby = babyTv.getText().toString();

                Person person = new Person();
                person.setAdult(adult);
                person.setChildren(children);
                person.setBaby(baby);

                firestore.collection("person").add(person)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("AddpersonActiviy", "성공");
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("AddpersonActiviy", e.getMessage());
                                            }
                                        });

                Toast.makeText(PersonActivity.this, "예약 완료되었습니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PersonActivity.this, DamsanActivity.class); //PersonActivity페이지로 넘어감
                startActivity(intent);
            }
        });
    }
}
