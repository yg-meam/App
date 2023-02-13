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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class JoinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        FirebaseAuth auth = FirebaseAuth.getInstance(); //FirebaseAuth 인증

       EditText emailEt = findViewById(R.id.email_et);
        EditText passwordEt = findViewById(R.id.password_et);

        Button joinBtn = findViewById(R.id.join_btn);

        joinBtn.setOnClickListener(new View.OnClickListener() { //'회원가입' 버튼 클릭 시
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                if(email.equals("")) { //이메일 칸이 비어있으면 Toast 발생
                    Toast.makeText(JoinActivity.this, "E-mail을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals("")) { //비밀번호 칸이 비어있으면 Toast 발생
                   Toast.makeText(JoinActivity.this, "Password를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password) //내가 입력한 이메일과 비밀번호를 인증
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() { //회원가입에 성공했을 때
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(JoinActivity.this, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() { //회원가입에 실패했을 때
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("JoinActivity", e.getMessage());
                                Toast.makeText(JoinActivity.this, "다시 입력해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                Intent intent = new Intent(JoinActivity.this, MainActivity.class); //WaitingActivity 페이지로 넘어감
                startActivity(intent);
            }
        });
    }
}
