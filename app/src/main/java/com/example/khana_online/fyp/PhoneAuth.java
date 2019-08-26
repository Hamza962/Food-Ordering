package com.example.khana_online.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

public class PhoneAuth extends AppCompatActivity {


    private Spinner spinner;
    private EditText editText,cusname,cusaddress;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_auth);

        ////////////////////////////////////////////////////////////

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , CountryData.countryNames));

        editText = findViewById(R.id.editTextPhone);

        Intent intent = getIntent();
        final String dishname = intent.getStringExtra("name");

        final String chefid = intent.getStringExtra("chefid");
        // final Intent intent = getIntent();
        final String dishprice = intent.getStringExtra("price");
        //final Intent intent = getIntent();
        final String deliverytype = intent.getStringExtra("type");
        final String quantity = intent.getStringExtra("quantity");


        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                final String phoneNumber = "+" + code + number;


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Blocked_User");

//                final Query query = databaseReference.orderByChild("phno");
                final Query query = databaseReference.orderByChild("phno").equalTo(phoneNumber);


                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String x = ds.child("phno").getValue(String.class);
                            if (x.equals(phoneNumber)) {
                                Toast.makeText(getApplicationContext(), "Your account has been blocked! Contact admin for further details", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                                cusname = findViewById(R.id.etName);
                                cusaddress = findViewById(R.id.et_Address);
                                String customername = cusname.getText().toString();
                                final String customeradd = cusaddress.getText().toString().trim();
                                Intent intent = new Intent(PhoneAuth.this, VerifyPhoneActivity.class);
                                intent.putExtra("quantity", quantity);
                                intent.putExtra("phonenumber", phoneNumber);
                                intent.putExtra("name", dishname);
                                intent.putExtra("chefid", chefid);
                                intent.putExtra("price", dishprice);
                                intent.putExtra("type", deliverytype);
                                intent.putExtra("cust_name", customername);
                                intent.putExtra("cust_add", customeradd);
                                startActivity(intent);
                            }

                        //DataSnapshot ds : dataSnapshot.getChildren();//getValue(String.class);


                        /*for (DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            String x = ds.child(phoneNumber).getValue(String.class);
                            assert x != null;
                            if (x.equals(phoneNumber))
                            {
                                Toast.makeText(getApplicationContext(), "Banned for some reasons", Toast.LENGTH_SHORT).show();
                            }
                            else
                            { cusname = findViewById(R.id.etName);
                                cusaddress = findViewById(R.id.et_Address);
                                String customername = cusname.getText().toString();
                                final String customeradd = cusaddress.getText().toString().trim();


                                Intent intent = new Intent(PhoneAuth.this, VerifyPhoneActivity.class);
                                intent.putExtra("quantity", quantity);

                                intent.putExtra("phonenumber", phoneNumber);
                                intent.putExtra("name", dishname);
                                intent.putExtra("chefid", chefid);
                                intent.putExtra("price", dishprice);
                                intent.putExtra("type", deliverytype);
                                intent.putExtra("cust_name", customername);
                                intent.putExtra("cust_add", customeradd);


                                startActivity(intent);}
                        }*/


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            /*        cusname = findViewById(R.id.etName);
                    cusaddress = findViewById(R.id.et_Address);
                    String customername = cusname.getText().toString();
                    final String customeradd = cusaddress.getText().toString().trim();


                    Intent intent = new Intent(PhoneAuth.this, VerifyPhoneActivity.class);
                    intent.putExtra("quantity", quantity);

                    intent.putExtra("phonenumber", phoneNumber);
                    intent.putExtra("name", dishname);
                    intent.putExtra("chefid", chefid);
                    intent.putExtra("price", dishprice);
                    intent.putExtra("type", deliverytype);
                    intent.putExtra("cust_name", customername);
                    intent.putExtra("cust_add", customeradd);


                    startActivity(intent);*/


                /////////////////////////////////////////





             /*   Intent intent = new Intent(VerifyPhoneActivity.this, Cart.class);
                intent.putExtra("phonenumber", phonenumber);
                intent.putExtra("name", dishname);
                intent.putExtra("chefid", chefid);
                intent.putExtra("price", dishprice);
                intent.putExtra("type", deliverytype);
                intent.putExtra("address",customeradd);
                intent.putExtra("customerid","hi");
                startActivity(intent);*/

            }
        });
    }

  /*  protected void onStart() {
        super.onStart();

       /* try {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            try
            {
                Intent intent = new Intent(this, VerifyPhoneActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), "First logout Cheff account", Toast.LENGTH_SHORT).show();

            }
        }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "logout Cheff account", Toast.LENGTH_SHORT).show();
        }
    }*/

}
