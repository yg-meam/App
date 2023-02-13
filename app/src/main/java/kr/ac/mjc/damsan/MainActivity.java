package kr.ac.mjc.damsan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText emailEt = findViewById(R.id.email_et);
        EditText passwordEt = findViewById(R.id.password_et);

        Button loginBtn = findViewById(R.id.login_btn);
        Button joinBtn = findViewById(R.id.join_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() { //'로그인' 버튼 클릭 시
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                if(email.equals("")) { //이메일 입력칸이 비어있을 때 Toast가 발생
                    Toast.makeText(MainActivity.this, "E-mail을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals("")) { //비밀번호 입력칸이 비어있을 때 Toast가 발생
                    Toast.makeText(MainActivity.this, "Password를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth auth = FirebaseAuth.getInstance(); //인증
                auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) { //FirebaseAuth에 있는 이메일과 비밀번호가 일치할 시 성공
                                Toast.makeText(MainActivity.this, "로그인이 완료되었습니다", Toast.LENGTH_SHORT).show();
                                Log.d("MainActivity", authResult.getUser().getEmail());
                                Intent intent = new Intent(MainActivity.this, DamsanActivity.class); //DamsanActivity 페이지로 넘어감
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() { //회원가입에 실패했을 때
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "다시 입력해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() { //'회원가입' 버튼 클릭 시
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class); //JoinActivity 페이지로 넘어감
                startActivity(intent);
            }
        });
    }
}