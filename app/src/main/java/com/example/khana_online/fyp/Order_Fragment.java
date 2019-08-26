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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Order_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Order_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Order_Fragment extends Fragment implements
        AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView dishrecycler;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Order_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");




        //////////////////////////////////////////////////////////////////////////////////////////

      /*  Spinner spin = (Spinner) view.findViewById(R.id.sp_status);
        spin.setOnItemSelectedListener(this);



        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        Map<String,Object> order_status = new HashMap<>();

        order_status.put("status",aa);*/


        /////////////////////////////////////////////////////////////////////////////////////////
        firebaseAuth = FirebaseAuth.getInstance();
        //  final


        dishrecycler = view.findViewById(R.id.order_recycler);
        dishrecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        dishrecycler.setLayoutManager(linearLayoutManager);
        // final Dishes dishes = new Dishes();
        displaypost();
       /* chefdishes = view.findViewById(R.id.dishes_fragment);
        chefdishes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getActivity() , add_dishes.class));
            }
        });*/
    }
    private void displaypost()
    {
        String usern;
        usern = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        Query query = databaseReference.orderByChild("chefid").equalTo(usern);
        FirebaseRecyclerOptions<Cart_class> options = new FirebaseRecyclerOptions.Builder<Cart_class>()
                .setQuery(query, Cart_class.class)
                .build();

        FirebaseRecyclerAdapter<Cart_class, Order_Fragment.OrderViewHolder> adapter = new FirebaseRecyclerAdapter<Cart_class, Order_Fragment.OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Order_Fragment.OrderViewHolder holder, final int position, @NonNull final Cart_class model) {
                final String[] status = { "Accepted", "Inprogress", "Delivered"};
                 String record;



             final ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,status);

                holder.spin.setAdapter(adapter);

                holder.upd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String status1 = status[holder.spin.getSelectedItemPosition()];
                        holder.spin.setSelection(adapter.getPosition(status1));
                        Map<String, Object> Order = new HashMap<>();
                        Order.put("quantity",model.getQuantity());
                        Order.put("phonenumber", model.getPhonenumber());
                        Order.put("customername", model.getCustomername());
                        Order.put("customerid", model.getCustomerid());
                        Order.put("address", model.getAddress());
                        Order.put("deliverytype", model.getDeliverytype());
                        Order.put("dishname", model.getDishname());
                        Order.put("chefid", model.getChefid());
                        Order.put("Total_Price", model.getTotal_Price());
                        Order.put("orderid", model.getOrderid());
                        Order.put("status",status1);
                        Order.put("time",model.getTime());
                        databaseReference.child(model.getOrderid()).setValue(Order);


                    }
                });

                holder.rep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("Blocked_User").child(model.getPhonenumber());
                        String x = model.getTotal_Price();
                        int amount = Integer.valueOf(x);
                        int fine = amount + 100;

                        Map<String, Object> Ban = new HashMap<>();
                        Ban.put("phno",model.getPhonenumber());
                        Ban.put("charges",fine);
                        data.setValue(Ban);
                    }
                });
                holder.Phone.setText(model.getPhonenumber());
                holder.Address.setText(model.getAddress());
                holder.time.setText(model.getTime());
                holder.price.setText(model.getTotal_Price());
                holder.Dishname.setText(model.getDishname());
                holder.Deliverytype.setText(model.getDeliverytype());
                holder.Quantity.setText(model.getQuantity());
                holder.cust_name.setText(model.getCustomername());





                final String key = model.getOrderid();
               // aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             //   final String S = holder.spin.getSelectedItem().toString();
           /*     holder.upd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        //Creating the ArrayAdapter instance having the country list
                        //Setting the ArrayAdapter data on the Spinner
                        //   hspin.setAdapter(aa);
                        Map<String,Object> order_status = new HashMap<>();

                      //  Cart_class c =new Cart_class(S);
                        order_status.put("status",S);
                        databaseReference.child(key).setValue(order_status);
                       // order_status.put("status","hjh");

                     //   databaseReference.child(key).setValue(order_status);
                    }
                });*/

              holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  Intent intent = new Intent(view.getContext(), Order_Info.class);
                        //view.getContext().startActivity(intent);
                     //   String key = model.getId();
                        databaseReference.child(key).removeValue();


                    }
                });


                           }

            @NonNull
            @Override
            public Order_Fragment.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orders_list, viewGroup, false);
                return new Order_Fragment.OrderViewHolder(view);
            }
        };
        dishrecycler.setAdapter(adapter);
        adapter.startListening();
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        //   public ImageView imageView;
        ImageButton btn;
        Button upd,rep;
    Spinner spin;
        public TextView Phone,Address,Deliverytype,Dishname, price, Quantity,cust_name,time;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            upd = itemView.findViewById(R.id.update);
            rep = itemView.findViewById(R.id.btn_report);
            spin = (Spinner) itemView.findViewById(R.id.sp_status);
            Phone = itemView.findViewById(R.id.phn);
            //customrename = itemView.findViewById(R.id.Name);
            Address = itemView.findViewById(R.id.address);
            time = itemView.findViewById(R.id.texttime);
            price = itemView.findViewById(R.id.price);
            Deliverytype = itemView.findViewById(R.id.Delivery);
            Dishname = itemView.findViewById(R.id.dishname);
            cust_name =itemView.findViewById(R.id.cust_name);
            Quantity = itemView.findViewById(R.id.quantity);
            btn= (ImageButton) itemView.findViewById(R.id.imageButton);
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Order_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Order_Fragment newInstance(String param1, String param2) {
        Order_Fragment fragment = new Order_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_, container, false);
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
}
