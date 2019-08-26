package com.example.khana_online.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {


    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText,cusname,cusaddress;

    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    final Intent intent = getIntent();
   /* String dishname = intent.getStringExtra("name");
    final String chefid = intent.getStringExtra("chefid");
    String deliverytype = intent.getStringExtra("type");
    // final Intent intent = getIntent();
    String dishprice = intent.getStringExtra("price");*/
    String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.editTextCode);

        phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);


        //final Intent intent = getIntent();

        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editText.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6/* && customername.isEmpty() && customeradd.isEmpty() */) {

                    editText.setError("Enter credantials...");
                    editText.requestFocus();

                    //if ();

                }
                else {
                    verifyCode(code);



                   // Intent intent = new Intent(VerifyPhoneActivity.this, Cart.class);

/*                    usern = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    Customer_class customer_class = new Customer_class(phonenumber,customername,"hi",customeradd);
                    databaseReference.child("hi"user)/*.setValue(customer_class);
                    cusname = findViewById(R.id.et_name);
                    cusaddress = findViewById(R.id.et_Address);
                    final String customername = cusname.getText().toString().trim();
                    final String customeradd = cusaddress.getText().toString().trim();
                    DatabaseReference databaseReference;
                    databaseReference = FirebaseDatabase.getInstance().getReference("Customer");
                    String usern;
                    intent.putExtra("phonenumber", phonenumber);
                    intent.putExtra("name", dishname);
                    intent.putExtra("chefid", chefid);
                    intent.putExtra("price", dishprice);
                    intent.putExtra("type", deliverytype);
                    intent.putExtra("address",customeradd);
                    intent.putExtra("customerid","hi");
                    */
                }
            }
        });

    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);

        //String x = Objects.requireNonNull(FirebaseAuth.getCurrentUser()).getUid();
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference databaseReference;
                            databaseReference = FirebaseDatabase.getInstance().getReference("Customer");
                            String usern = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            Intent intent = new Intent(VerifyPhoneActivity.this, Cart.class);
                            String cust_add = getIntent().getStringExtra("cust_add");
                            String cust_name = getIntent().getStringExtra("cust_name");
                            String usern1 = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                            String dishname = getIntent().getStringExtra("name");
                            final String chefid = getIntent().getStringExtra("chefid");
                            String deliverytype = getIntent().getStringExtra("type");
                            // final Intent intent = getIntent();
                            String dishprice = getIntent().getStringExtra("price");

                            final String quantity = getIntent().getStringExtra("quantity");

                            String token = FirebaseInstanceId.getInstance().getToken();

                            Map<String,Object> customer = new HashMap<>();
                                Toast.makeText(getApplicationContext(),"Not null",Toast.LENGTH_LONG).show();
                                customer.put("phonenumber", phonenumber);
                                customer.put("customer_name", cust_name);
                                customer.put("customerid", usern1);
                                customer.put("address", cust_add);
                                customer.put("token", token);
                                databaseReference.child(usern).setValue(customer);


                             //Customer_class customer_class = new Customer_class(phonenumber,cust_name,usern1,cust_add,token);
                           // databaseReference.child(usern).setValue(customer_class);
                            intent.putExtra("address",cust_add);
                            intent.putExtra("custname",cust_name);

                            intent.putExtra("phonenumber", phonenumber);
                            intent.putExtra("name", dishname);
                            intent.putExtra("chefid", chefid);
                            intent.putExtra("price", dishprice);
                            intent.putExtra("type", deliverytype);
                            intent.putExtra("quantity", quantity);


                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);

                        } else {
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText(code);


////////////////////////////////////////////////////////////////
        /*        String usern;
                usern = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(usern);
                //FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                //DatabaseReference myref = firebaseDatabase.getReference("Customer").child(mAuth.getUid());
                CustomerDatabase customerDatabase = new CustomerDatabase(name,add,des);
                databaseReference.setValue(customerDatabase);*/
                verifyCode(code);

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}
