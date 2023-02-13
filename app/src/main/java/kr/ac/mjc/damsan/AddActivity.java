package kr.ac.mjc.damsan;

import static android.content.Intent.ACTION_PICK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddActivity extends AppCompatActivity {

    ImageView imageIv;
    EditText nameTv;
    EditText priceTv;
    EditText textTv;
    Button addBtn;

    final int REQ_IMAGE_PICK = 1000;
    Uri selectedImage;

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseStorage storage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        imageIv = findViewById(R.id.image_iv);
        nameTv = findViewById(R.id.food_tv);
        priceTv = findViewById(R.id.food_price_tv);
        textTv = findViewById(R.id.food_text_tv);
        addBtn = findViewById(R.id.add_btn);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, REQ_IMAGE_PICK);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = UUID.randomUUID().toString();
                storage.getReference().child("image").child(fileName)
                        .putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Log.d("AddFragment", uri.toString());
                                                Toast.makeText(AddActivity.this, "메뉴가 추가되었습니다", Toast.LENGTH_SHORT).show();

                                                Menu menu = new Menu();
                                                String name = nameTv.getText().toString();
                                                String price = priceTv.getText().toString();
                                                String text = textTv.getText().toString();
                                                menu.setName(name);
                                                menu.setPrice(price);
                                                menu.setText(text);
                                                menu.setImageUrl(uri.toString());

                                                uploadMenu(menu);
                                            }
                                        });
                            }
                        });

                Intent intent = new Intent(AddActivity.this, DamsanActivity.class); //
                startActivity(intent);
            }
        });
    }

    public void uploadMenu(Menu menu) {
        firestore.collection("menu").add(menu)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_IMAGE_PICK && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            imageIv.setImageURI(selectedImage);
        }
    }
}
