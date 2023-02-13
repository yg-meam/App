package kr.ac.mjc.damsan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DamsanActivity extends AppCompatActivity {

    private int count = 0; //대기 명수 초기화
    private int minute = 0; //시간 초기화

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damsan);

        TextView waitingTv = findViewById(R.id.waiting_tv);
        TextView timeTv = findViewById(R.id.time_tv);

        Button receiptBtn = findViewById(R.id.receipt_btn);
        Button menuBtn = findViewById(R.id.menu_btn);

        receiptBtn.setOnClickListener(new View.OnClickListener() { //'대기 접수'버튼 클릭 시
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DamsanActivity.this, WaitingActivity.class); //WaitingActivity 페이지로 넘어감
                startActivity(intent);

                count++; //1명씩 증가
                minute+=40; //40분씩 증가
                waitingTv.setText("현재 대기 " + count + "" + "팀");
                timeTv.setText("예상 대기 시간 약 " + minute + "" + "분");
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DamsanActivity.this, MenuActivity.class); //WaitingActivity 페이지로 넘어감
                startActivity(intent);
            }
        });
    }
}
