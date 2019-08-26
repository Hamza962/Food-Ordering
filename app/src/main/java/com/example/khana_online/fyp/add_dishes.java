package com.example.khana_online.fyp;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class add_dishes extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener
{
    String usern;
    private DatabaseReference databaseReference,databaseReference1;
    private FirebaseAuth firebaseAuth;
    private EditText DishName,DishCata,DishQuan, Dishprice;
    private Button AddDish,picktime;
    private ImageView dishpic;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private  TextView textView;
    private static int PICK_IMAGE = 123;
    ProgressDialog progress;
    Uri imagepath;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null)
        {
            imagepath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dishpic.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dishes);
        DishName = findViewById(R.id.et_dish_name);
        DishCata = findViewById(R.id.et_dish_cat);
        DishQuan = findViewById(R.id.et_dish_quantity);
        AddDish = findViewById(R.id.btn_add_dish);
        dishpic = findViewById(R.id.iv_dish_pic);
        picktime = findViewById(R.id.btn_add);
        Dishprice = findViewById(R.id.et_dish_Price);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        dishpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"),PICK_IMAGE);

            }
        });

        picktime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePiker();
                timepicker.show(getSupportFragmentManager(),"Time Piker");
            }
        });


        usern = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Dishes");

        AddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    savedish();
                }
                catch( Exception e) {
                    Toast.makeText(getApplicationContext(), "All Fields are required", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void savedish() {

        databaseReference1=FirebaseDatabase.getInstance().getReference("Chef");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cheff c = new cheff();
                final String x = c.getName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final String name = DishName.getText().toString().trim();
        final String catagory = DishCata.getText().toString().trim();
        final String quantity = DishQuan.getText().toString().trim();
        final String price = Dishprice.getText().toString().trim();
        final String chefid;
        final String time = textView.getText().toString().trim();
        chefid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final String id = databaseReference.push().getKey();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(catagory) && !TextUtils.isEmpty(quantity) && imagepath != null && !TextUtils.isEmpty(price)) {

            progress=new ProgressDialog(this);
            progress.setMessage("Uploading Dish....");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
            final StorageReference imageReferance = storageReference.child("images").child(chefid).child(id).child("Dish pics");
            imageReferance.putFile(imagepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess( final Uri uri) {
                            databaseReference=FirebaseDatabase.getInstance().getReference("Dishes");
                            databaseReference1 =FirebaseDatabase.getInstance().getReference("Chef").child(usern);
                            databaseReference1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    cheff chefprofile = dataSnapshot.getValue(cheff.class);
                                    assert chefprofile != null;
                                    String chefname = chefprofile.getName();

/*
                                    Map<String,Object> dish = new HashMap<>();

                            dish.put("name",name);
                            dish.put("category",catagory);
                            dish.put("quantity",quantity);
                            dish.put("chef_id",chefid);
                            dish.put("imageUrl",imgurl);
                            dish.put("price",price);
                            dish.put("time",time);
                            dish.put("Chef Name",chefname);
                            */
                                    String imgurl= uri.toString();
                                    Dishes dish = new Dishes(name,catagory,quantity,chefid,imgurl,price,time,chefname,id);

                                    databaseReference.child(id).setValue(dish);
                                    Toast.makeText(getApplicationContext(), "Dish Uploaded", Toast.LENGTH_LONG).show();
                                    progress.hide();
                                    Intent intent = new Intent(getApplicationContext(),profile.class);
                                    startActivity(intent);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    });
                }
            });

        } else {
            Toast.makeText(this, "All Fields Required", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        textView = findViewById(R.id.et_time);
        textView.setText( hourOfDay +" : "+minute);
    }
}