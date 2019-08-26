package com.example.khana_online.fyp;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

import java.util.Objects;

public class Home extends AppCompatActivity {

    private RecyclerView dishrecycler;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    public FirebaseOptions options;
    private Button btnsearch;
    private EditText etsearch;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //assert user != null;
        // String id= user.getUid();
        String usern;
//        usern = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //.child("/");
        //databaseReference.keepSynced(true);

        Button btn = findViewById(R.id.btn_cheff);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Top_cheff.class);
                startActivity(intent);

            }
        });
        dishrecycler = findViewById(R.id.dishes_recycler_view);
        dishrecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        dishrecycler.setLayoutManager(linearLayoutManager);

        displaypost();

        btnsearch = findViewById(R.id.btn_search);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasesearch();
            }
        });
        etsearch = findViewById(R.id.et_search);
        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              //  firebasesearch();
                firebasesearch1();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            //    firebasesearch();
                firebasesearch1();
            }

            @Override
            public void afterTextChanged(Editable s) {
                firebasesearch();
            }
        });

    }

    private void firebasesearch()
    {


        String text = etsearch.getText().toString().trim();
        Query query = databaseReference.child("Dishes").orderByChild("name").startAt(text).endAt(text + "\uf8ff");
        FirebaseRecyclerOptions<Dishes> options = new FirebaseRecyclerOptions.Builder<Dishes>()
                .setQuery(query, Dishes.class)
                .build();

        FirebaseRecyclerAdapter<Dishes, DishesViewHolder> adapter = new FirebaseRecyclerAdapter<Dishes, DishesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DishesViewHolder holder, final int position, @NonNull Dishes model) {
                holder.dish.setText(model.getName());
                holder.chefname.setText(model.getChefname());
                holder.price.setText(model.getPrice());
                holder.time.setText(model.getTime());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TextView textView;
                        //  String x = holder.findViewById(R.id.Dish_Title);
                        Intent intent = new Intent(view.getContext(), Order_Info.class);
                        view.getContext().startActivity(intent);



                    }
                });
            }

            @NonNull
            @Override
            public DishesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dishlayout, viewGroup, false);
                return new DishesViewHolder(view);
            }
        };
        dishrecycler.setAdapter(adapter);
        adapter.startListening();
    }

    private void firebasesearch1()
    {


        String text = etsearch.getText().toString().trim();
        Query query = databaseReference.child("Dishes").orderByChild("category").startAt(text).endAt(text + "\uf8ff");
        FirebaseRecyclerOptions<Dishes> options = new FirebaseRecyclerOptions.Builder<Dishes>()
                .setQuery(query, Dishes.class)
                .build();

        FirebaseRecyclerAdapter<Dishes, DishesViewHolder> adapter = new FirebaseRecyclerAdapter<Dishes, DishesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DishesViewHolder holder, final int position, @NonNull Dishes model) {
                holder.dish.setText(model.getName());
                holder.chefname.setText(model.getChefname());
                holder.price.setText(model.getPrice());
                holder.time.setText(model.getTime());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TextView textView;
                        //  String x = holder.findViewById(R.id.Dish_Title);
                        Intent intent = new Intent(view.getContext(), Order_Info.class);
                        view.getContext().startActivity(intent);



                    }
                });
            }

            @NonNull
            @Override
            public DishesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dishlayout, viewGroup, false);
                return new DishesViewHolder(view);
            }
        };
        dishrecycler.setAdapter(adapter);
        adapter.startListening();
    }
   /* private void firebaseUserSearch(String searchText) {

        Toast.makeText(Home.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = d.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(

                Users.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getStatus(), model.getImage());

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }
*/

    private void displaypost() {
        Query query = databaseReference.child("Dishes");
        FirebaseRecyclerOptions<Dishes> options = new FirebaseRecyclerOptions.Builder<Dishes>()
                .setQuery(query, Dishes.class)
                .build();

        FirebaseRecyclerAdapter<Dishes, DishesViewHolder> adapter = new FirebaseRecyclerAdapter<Dishes, DishesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DishesViewHolder holder, final int position, @NonNull final Dishes model) {
                holder.dish.setText(model.getName());
                holder.chefname.setText(model.getChefname());
                holder.price.setText(model.getPrice());
                holder.time.setText(model.getTime());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
                String chefid = model.getChefid();

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

                                holder.ratingBar.setRating(average);
                                holder.ratingBar.setFocusable(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String x = model.getName();
                        String chef = model.getChefname();
                        String price = model.getPrice();
                        String quantity = model.getQuantity();
                        String y = model.getImageUrl();
                        String cat= model.getCategory();
                        String chefid = model.getChefid();






                        Intent intent = new Intent(Home.this, Order_Info.class);
                        intent.putExtra("chefid", chefid);
                        intent.putExtra("message", x);
                        intent.putExtra("message1", y);
                        intent.putExtra("message2", chef);
                        intent.putExtra("message3",price);
                        intent.putExtra("message4", quantity);
                        intent.putExtra("message5",cat);
                        startActivity(intent);

                        /*
                        Intent intent = new Intent(view.getContext(), Order_Info.class);
                        view.getContext().startActivity(intent);*/
                    }
                });
            }

            @NonNull
            @Override
            public DishesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dishlayout, viewGroup, false);
                return new DishesViewHolder(view);
            }
        };
        dishrecycler.setAdapter(adapter);
        adapter.startListening();
    }

    public static class DishesViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RatingBar ratingBar;
        public TextView  time , dish, chefname,price,catogary;
        public DishesViewHolder(@NonNull View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.bar);
            imageView = itemView.findViewById(R.id.post_image);
            dish = itemView.findViewById(R.id.Dish_Title);
            chefname = itemView.findViewById(R.id.Chef_name);
            price = itemView.findViewById(R.id.Dish_Price);
            time = itemView.findViewById(R.id.Available);
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
