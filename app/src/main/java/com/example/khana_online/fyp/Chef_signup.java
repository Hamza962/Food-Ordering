package com.example.khana_online.fyp;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Chef_signup extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText editTextEmail, editTextPassword, textname,textphnumber,textaddress,sp;
    private cheff chef;
    private ImageView ProfilePic_Chef;
    FirebaseDatabase database;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    String name, email, phnumber, address, password,id,s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_signup);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        textname = (EditText) findViewById(R.id.textname);
        textphnumber = (EditText) findViewById(R.id.textphnumber);
        textaddress = (EditText) findViewById(R.id.textaddress);
        sp = (EditText) findViewById(R.id.textspec);
      //  ProfilePic_Chef = (ImageView) findViewById(R.id.imageViewprofile);
        //ref =FirebaseDatabase.getInstance().getReference("cheff");
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("cheff");
        mAuth = FirebaseAuth.getInstance();
        chef = new cheff();
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }


    private void registerUser() {
         email = editTextEmail.getText().toString().trim();
         password = editTextPassword.getText().toString().trim();
         phnumber = textphnumber.getText().toString().trim();
         address = textaddress.getText().toString().trim() ;
         name = textname.getText().toString().trim();
         s = sp.getText().toString();
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            textname.setError("Name is required");
            textname.requestFocus();
            return;
        }

        if (name.length() < 4) {
            textname.setError("Minimum lenght of name should be 4");
            textname.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            textaddress.setError("Address is required");
            textaddress.requestFocus();
            return;
        }

        if (phnumber.isEmpty()) {
            textphnumber.setError("Phone number is required");
            textphnumber.requestFocus();
            return;
        }
        if (phnumber.length() != 11  ) {
            textphnumber.setError("Length should be 11");
            textphnumber.requestFocus();
            return;
        }
        if (s.isEmpty()) {
            sp.setError("Speciality is required");
            sp.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(),"User register Success full",Toast.LENGTH_SHORT).show();
                    sendEmailVerificaation();
                } else {
                    Toast.makeText(getApplicationContext(),"Some Error Occured",Toast.LENGTH_SHORT).show();
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        /*if (!TextUtils.isEmpty(name))
        {
            String id = ref.push().getKey();
            cheff chef = new cheff(name,email,phnumber,address,id,password);
            ref.child(id).setValue(chef);
            Toast.makeText(getApplicationContext(),"cheff register Success full",Toast.LENGTH_SHORT).show();
            return;

        }*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                registerUser();
                //add_chef();
               /* chef.setEmail(editTextEmail.getText().toString());
                chef.setAddress(textaddress.getText().toString());
                chef.setName(textname.getText().toString());
                chef.setPhno(textphnumber.getText().toString());
                chef.setPass(editTextPassword.getText().toString());
                ref.child(chef.getName()).setValue(chef);*/
                break;

            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, Chef_Signin.class));
                break;
        }
    }
    private void sendEmailVerificaation ()
    {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        senduserdata();
                        Toast.makeText(Chef_signup.this,"Verification Email Sent",Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(Chef_signup.this, Chef_Signin.class));
                    }
                    else
                    {
                        Toast.makeText(Chef_signup.this,"Error in sending Varification Mail",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
    private void senduserdata()
    {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference("Chef").child(mAuth.getUid());
        String usern = mAuth.getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
///////////////////////////////////////////////////////////////////
        Map<String, Object> Order = new HashMap<>();
        Order.put("name", name);
        Order.put("email", email);
        Order.put("phno", phnumber);
        Order.put("address", address);
        Order.put("devicetoken",token );
        Order.put("id", usern);
        Order.put("spec", s);
        myref.setValue(Order);
        /////////////////////////////////////////////////////////////
      //  String token = FirebaseInstanceId.getInstance().getToken();
        cheff chef_profile = new cheff(name,email,phnumber,address,token,usern,s);
        myref.setValue(chef_profile);

    }
}
