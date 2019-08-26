package com.example.khana_online.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order_Status extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private FirebaseAuth mAuth;
    Button btn;
    RatingBar ratingBar;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__status);

        //Spinner spinner = findViewById(R.id.spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Order_Status.this,R.array.type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);
        ratingBar = findViewById(R.id.rating_bar);
        btn = findViewById(R.id.btn_Rate);
        final TextView s = findViewById(R.id.txt_status);
        final String usrn = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();



        ImageButton imageButton = findViewById(R.id.btn_reresh);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
                final Query query = databaseReference.orderByChild("customerid").equalTo(usrn);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            String x = ds.child("status").getValue(String.class);
                            s.setText(x);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

       /* Query  query = firebaseDatabase.getReference("Orders").orderByChild("customerid").equalTo(usrn);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Cart_class cart = dataSnapshot.getValue(Cart_class.class);
                assert cart != null;
                s.setText(cart.getStatus());


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               float c = ratingBar.getRating();

               String rating = String.valueOf(c);
               Toast.makeText(Order_Status.this, rating,Toast.LENGTH_LONG).show();

                final DatabaseReference databaseReference,ref;
                FirebaseAuth firebaseAuth;
                Intent intent = getIntent();
                final String chefid = intent.getStringExtra("chefid");
                final String id = intent.getStringExtra("id");

                databaseReference = firebaseDatabase.getReference("Chef").child(chefid).child("Rating").child(id);

                databaseReference.setValue(c);

                FirebaseDatabase.getInstance().getReference("Chef").child(chefid).child("Rating")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                float total = 0;
                                float count = 0;
                                float average = 0;

                                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                    float rating = ds.getValue(Float.class);
                                    total = total + rating;
                                    count = count + 1;
                                    average = total / count;
                                }
                                DatabaseReference ref = firebaseDatabase.getReference("Chef").child(chefid).child("Average");
                             //   holder.ratingBar.setRating(average);
                                Map<String, Object> Ave = new HashMap<>();
                                Ave.put("Average", average);
                                ref.setValue(Ave);
                               // holder.ratingBar.setFocusable(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
