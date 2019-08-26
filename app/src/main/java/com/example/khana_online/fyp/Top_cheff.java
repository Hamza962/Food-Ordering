package com.example.khana_online.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Top_cheff extends AppCompatActivity
{
    private RecyclerView dishrecycler;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    public FirebaseOptions options;

String x;
    String barcode,barcode1;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_cheff);

        firebaseAuth = FirebaseAuth.getInstance();

        // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //assert user != null;
        // String id= user.getUid();
        String usern;
//        usern = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        dishrecycler = findViewById(R.id.chef_recycler_view);
        dishrecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        dishrecycler.setLayoutManager(linearLayoutManager);
///////////////////////////////////////////////////////////////////////////////////////

        DatabaseReference db = FirebaseDatabase .getInstance() .getReference("Chef");


        displaypost();

    }
    private void displaypost() {
        DatabaseReference db = FirebaseDatabase .getInstance() .getReference("Chef");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // x = dataSnapshot.getKey();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // arrayAdapter.notifyDataSetChanged();
                    final String barcode =  snapshot.getKey();
                    assert barcode != null;
///

                               // DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Chef").child(barcode);
                                Query query = databaseReference.child("Chef");//child("Average").child("Average");//.orderByChild("Average");//.child(x);

                             //    Toast.makeText(Top_cheff.this,barcode,Toast.LENGTH_SHORT).show();

                                FirebaseRecyclerOptions<cheff> options = new FirebaseRecyclerOptions.Builder<cheff>()
                                        .setQuery(query, cheff.class)
                                        .build();


                                FirebaseRecyclerAdapter<cheff, Top_cheff.DishesViewHolder> adapter = new FirebaseRecyclerAdapter<cheff, Top_cheff.DishesViewHolder>(options) {
                                    @Override

                                    protected void onBindViewHolder(@NonNull final Top_cheff.DishesViewHolder holder, final int position, @NonNull final cheff model) {
                                        String chefid = model.getId();

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

                                                      //  if (average>=4)
                                                       // {
                                                            holder.ratingBar.setRating(average);
                                                            holder.ratingBar.setFocusable(false);
                                                            holder.chefname.setText(model.getName());
                                                            holder.spec.setText(model.getSpec());

                                                       // }
                                                        /*else
                                                        {
                                                            // Toast.makeText(Top_cheff.this,"Nothing to show",Toast.LENGTH_SHORT).show();
                                                        }*/
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
/*
                holder.chefname.setText(model.getName());
                holder.spec.setText(model.getAddress());
*/

                                    }

                                    @NonNull
                                    @Override
                                    public Top_cheff.DishesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.status_card, viewGroup, false);
                                        return new Top_cheff.DishesViewHolder(view);
                                    }
                                };
                                dishrecycler.setAdapter(adapter);
                                adapter.startListening();






                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static class DishesViewHolder extends RecyclerView.ViewHolder {
        public RatingBar ratingBar;
        public TextView chefname, spec;
        public DishesViewHolder(@NonNull View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.rating);
            chefname = itemView.findViewById(R.id.tv_Name);
            spec = itemView.findViewById(R.id.tv_special);
            // catogary = itemView.findViewById(R.id.et_dish_cat);
        }

    }


    /*public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String userName, String userStatus, String userImage){

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView user_status = (TextView) mView.findViewById(R.id.status_text);
            ImageView user_image = (ImageView) mView.findViewById(R.id.profile_image);


            user_name.setText(userName);
            user_status.setText(userStatus);

            Glide.with(ctx).load(userImage).into(user_image);


        }

    }*/
}