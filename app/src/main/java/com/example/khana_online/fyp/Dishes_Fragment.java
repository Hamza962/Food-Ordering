package com.example.khana_online.fyp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Dishes_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Dishes_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dishes_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView chefdishes;
    private RecyclerView dishrecycler;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    public FirebaseOptions options;
    ProgressBar P;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Dishes_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dishes_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Dishes_Fragment newInstance(String param1, String param2) {
        Dishes_Fragment fragment = new Dishes_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
      //  final
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Dishes");

        dishrecycler = view.findViewById(R.id.yourdish_recyclerview);
        dishrecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        dishrecycler.setLayoutManager(linearLayoutManager);
       // final Dishes dishes = new Dishes();
      displaypost();
        chefdishes = view.findViewById(R.id.dishes_fragment);
        chefdishes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getActivity() , add_dishes.class));
            }
        });

       registerForContextMenu(chefdishes);

    }


    /*   @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.menu1);

    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dishes_, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void displaypost()
    {
        final String usern;
        usern = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        Query query = databaseReference.orderByChild("chefid").equalTo(usern);
        FirebaseRecyclerOptions<Dishes> options = new FirebaseRecyclerOptions.Builder<Dishes>()
                .setQuery(query, Dishes.class)
                .build();

        FirebaseRecyclerAdapter<Dishes, Dishes_Fragment.DishesViewHolder> adapter = new FirebaseRecyclerAdapter<Dishes, Dishes_Fragment.DishesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Dishes_Fragment.DishesViewHolder holder, final int position, @NonNull final Dishes model) {
                holder.textView.setText(model.getName());
                holder.chefname.setText(model.getChefname());
                holder.price.setText(model.getPrice());
                holder.time.setText(model.getTime());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
               // final String x = model.getId();


                FirebaseDatabase.getInstance().getReference("Chef").child(usern).child("Rating")
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
                        Intent intent = new Intent(view.getContext(), Edit_Dish.class);

                        String x = model.getId();
                        intent.putExtra("dishid", x);
                        intent.putExtra("dishname", model.getName());
                        intent.putExtra("dishprice", model.getPrice());
                        intent.putExtra("time", model.getTime());
                        intent.putExtra("catogary", model.getCategory());
                        intent.putExtra("quantity", model.getQuantity());

                        view.getContext().startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public Dishes_Fragment.DishesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dishlayout, viewGroup, false);
                return new Dishes_Fragment.DishesViewHolder(view);
            }
        };
        dishrecycler.setAdapter(adapter);
        adapter.startListening();
    }
    public static class DishesViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RatingBar ratingBar;
        public TextView textView, chefname, price, time;
        public DishesViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.bar);
            imageView = itemView.findViewById(R.id.post_image);
            textView = itemView.findViewById(R.id.Dish_Title);
            chefname = itemView.findViewById(R.id.Chef_name);
            price = itemView.findViewById(R.id.Dish_Price);
            time = itemView.findViewById(R.id.Available);

        }
    }

}