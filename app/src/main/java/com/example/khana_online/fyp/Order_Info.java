package com.example.khana_online.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Order_Info extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RadioButton radioButton;
    RadioGroup radioGroup;

    String type[] = {"Self Pickup" , "Cash on Delivery"};//["Self Pickup" , "Cash on Delivery"];
    EditText text;
    String record;
    ArrayAdapter<String> adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__info);
         final Intent intent = getIntent();
        final String message = intent.getStringExtra("message");
        final TextView textView = findViewById(R.id.tvDishName);
        textView.setText(message);

        final String message1 = intent.getStringExtra("message1");
        ImageView imageView = (ImageView) findViewById(R.id.dishimg);
        Picasso.get().load(message1).into(imageView);



        final String message2 = intent.getStringExtra("message2");
        final TextView chefname = findViewById(R.id.tvChefffName);
        chefname.setText(message2);


     final String message3 = intent.getStringExtra("message3");
        final TextView price = findViewById(R.id.tvdish_price);
        price.setText(message3);


        final String message4 = intent.getStringExtra("message4");
        TextView textView4 = findViewById(R.id.Av_quant);
        textView4.setText(message4);
       final String message5 = intent.getStringExtra("message5");
        final TextView textView5 = findViewById(R.id.tvdishcata);
        textView5.setText(message5);

        final String chefid  = intent.getStringExtra("chefid");
        Button btn;
        btn = findViewById(R.id.btn_addtobasket);

        final Spinner spinner = findViewById(R.id.spinner);



        //final String o = spinner.getSelectedItem().toString();
        //String x = spinner.getSelectedItem().toString().trim();
       // Toast.makeText(getApplicationContext(), o, Toast.LENGTH_LONG).show();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,type);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        record = "Self Pickup";
                        break;
                    case 1:
                        record = "Cash on Delivery";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                text= findViewById(R.id.quantity);
                String Enter_quantity = text.getText().toString().trim();
                int a = Integer.valueOf(Enter_quantity);
                int b= Integer.valueOf(message4);
                if(a>b&& a!=0){

                    Toast.makeText(getApplicationContext(),"Cannot exceed Available Quantity",Toast.LENGTH_LONG).show();
                    return;
                }

                if(Enter_quantity!=null) {
                    final int singleprice = Integer.valueOf(message3);
                    final int Value = Integer.valueOf(Enter_quantity);
                    final int netprice = singleprice * Value;
                    final String Total_charges = String.valueOf(netprice);
                    Intent intent = new Intent(Order_Info.this, PhoneAuth.class);
                    intent.putExtra("name", message);
                    intent.putExtra("chefid", chefid);
                    intent.putExtra("price",Total_charges);
                    intent.putExtra("quantity", Enter_quantity);
                    intent.putExtra("type", record);
                    startActivity(intent);

                }

                else
                {
                    Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
                }

               // startActivity(new Intent(Order_Info.this , PhoneAuth.class));


               /*
                databaseReference=FirebaseDatabase.getInstance().getReference("Order");
                final String name = textView.toString().trim();
                final String price = textView3.getText().toString().trim();
                final String o = spinner.getSelectedItem().toString();
                //final String catagory = textView5.getText().toString().trim();

                Cart_class cart_class = new Cart_class(message,chefid,price,o);
                final String order_id = databaseReference.push().getKey();
                assert order_id != null;
                databaseReference.child(order_id).setValue(cart_class);
                Toast.makeText(getApplicationContext(), "Order placed", Toast.LENGTH_LONG).show();*/

            }
        });
    }

    public  void  checkButton(View view)
    {


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

       // String text = parent.getItemAtPosition(position).toString().trim();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
