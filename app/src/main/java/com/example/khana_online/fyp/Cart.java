package com.example.khana_online.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity {

    TextView cusnumber,deliverymethode,cusaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
/*
        radioGroup = findViewById(R.id.radio);
*/
        final Intent intent = getIntent();
        final String phonenumber = getIntent().getStringExtra("phonenumber");
        final String dishname = intent.getStringExtra("name");
        final String chefid = intent.getStringExtra("chefid");

        final String customername = intent.getStringExtra("custname");
       // final String customerid = intent.getStringExtra("customerid");
        final String dishprice = intent.getStringExtra("price");
        final String deliverytype = intent.getStringExtra("type");
        final String address = getIntent().getStringExtra("address");
        final String quantity = intent.getStringExtra("quantity");

        final String usern;
        usern = FirebaseAuth.getInstance().getCurrentUser().getUid();

       TextView Price = findViewById(R.id.dish_price);
       Price.setText(dishprice);
        TextView Name = findViewById(R.id.DishName);
        Name.setText(dishname);
        TextView No = findViewById(R.id.Phone_number);
        No.setText(phonenumber);
        TextView Methode = findViewById(R.id.delivery);
        Methode.setText(deliverytype);
        TextView Address = findViewById(R.id.address);
        Address.setText(address);

        Button button,button1;

        button = findViewById(R.id.btn_addtocart);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference,databaseReference1;
                databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
                databaseReference1 = FirebaseDatabase.getInstance().getReference("Notifications");
                final String id = databaseReference.push().getKey();
                final String id1 = databaseReference1.push().getKey();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String time = format.format(calendar.getTime());


             /*  final String usern;
                usern = FirebaseAuth.getInstance().getCurrentUser().getUid();*/
                 Map<String, Object> Order = new HashMap<>();
                 Order.put("quantity", quantity);
                 Order.put("phonenumber", phonenumber);
                 Order.put("customername", customername);
                 Order.put("customerid", usern);
                 Order.put("address", address);
                 Order.put("deliverytype", deliverytype);
                 Order.put("dishname", dishname);
                 Order.put("chefid", chefid);
                 Order.put("Total_Price", dishprice);
                 Order.put("orderid", id);
                 Order.put("status","Unseen");
                 Order.put("time",time);
                 databaseReference.child(id).setValue(Order);



                //Cart_class cart_class = new Cart_class(dishname,chefid,deliverytype,address,dishprice,usern,"1");
                //databaseReference.child(id).setValue(cart_class);
                String token = FirebaseInstanceId.getInstance().getToken();
                Notification notification = new Notification(usern,token,deliverytype,chefid);
              //  assert id != null;
                assert id1 != null;

              //  databaseReference.child(id).setValue(cart_class);
                databaseReference1.child(chefid).child(id1).setValue(notification);
                Toast.makeText(getApplicationContext(), "Order placed", Toast.LENGTH_LONG).show();

            }
        });

        button1=findViewById(R.id.btn_status);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this,Order_Status.class);

                intent.putExtra("chefid", chefid);
                intent.putExtra("id", usern);
                startActivity( intent);
            }
        });

//        NotificationManager.A

    }
}
