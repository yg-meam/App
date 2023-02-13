package kr.ac.mjc.damsan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    RecyclerView menulistRv;
    MenuAdapter menuAdapter;
    ArrayList<Menu> mMenuList = new ArrayList<Menu>();

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menulistRv = findViewById(R.id.menu_list_rv);
        menuAdapter = new MenuAdapter(mMenuList);
        menulistRv.setAdapter(menuAdapter);

        Button menuaddBtn = findViewById(R.id.menu_add_btn);

        menuaddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, AddActivity.class); //PersonActivity페이지로 넘어감
                startActivity(intent);
            }
        });

        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        menulistRv.setLayoutManager(layoutManager);
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("menu").orderBy("uploadDate", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot:documents) {
                            Menu menu = snapshot.toObject(Menu.class);
                            mMenuList.add(menu);
                        }
                        menuAdapter.notifyDataSetChanged();
                    }
                });
    }
}
