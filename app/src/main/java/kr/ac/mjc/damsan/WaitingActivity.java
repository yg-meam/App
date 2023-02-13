package kr.ac.mjc.damsan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class WaitingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        EditText numberEt = findViewById(R.id.number_et);

        Button btn [] = new Button[12]; //모든 버튼을 배열로 정렬
        btn[0] = findViewById(R.id.btn0_btn);
        btn[1] = findViewById(R.id.btn1_btn);
        btn[2] = findViewById(R.id.btn2_btn);
        btn[3] = findViewById(R.id.btn3_btn);
        btn[4] = findViewById(R.id.btn4_btn);
        btn[5] = findViewById(R.id.btn5_btn);
        btn[6] = findViewById(R.id.btn6_btn);
        btn[7] = findViewById(R.id.btn7_btn);
        btn[8] = findViewById(R.id.btn8_btn);
        btn[9] = findViewById(R.id.btn9_btn);
        btn[10] = findViewById(R.id.ok_btn);
        btn[11] = findViewById(R.id.clear_btn);

        for (int i=0; i<12;i++) {
            btn[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Button btn = (Button) view;
                    numberEt.append(btn.getText().toString()); //버튼 누르면 해당 숫자가 나옴
                    String number = numberEt.getText().toString();

                    if (btn.getText().toString().equals("Clear")) {
                        numberEt.setText(""); //clear버튼 누르면 초기화됨
                    }
                    if (btn.getText().toString().equals("Ok")) {
                        numberEt.setText(""); //'접수하기' 버튼 클릭 시 초기화됨

                        PhoneNumber phoneNumber = new PhoneNumber();
                        phoneNumber.setNumber(number);

                        firestore.collection("PhoneNumber").add(phoneNumber)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("AddphonenumberActivity", "성공");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("AddphonenumberAvtivity", e.getMessage());
                                    }
                                });

                        Intent intent = new Intent(WaitingActivity.this, PersonActivity.class); //PersonActivity페이지로 넘어감
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
