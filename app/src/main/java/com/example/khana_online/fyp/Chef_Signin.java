package com.example.khana_online.fyp;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chef_Signin extends AppCompatActivity {
    EditText chef_email, chef_pass;
    private ProgressBar progressDialog;
    private FirebaseAuth mauth;
    private DatabaseReference reff;
    private TextView forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_signin);

        chef_email = findViewById(R.id.text_chef_login_email);
        chef_pass = findViewById(R.id.text_chef_loginpass);
        forgotpass = findViewById(R.id.tvforgotpass);
        reff = FirebaseDatabase.getInstance().getReference("cheff");
        Button yourButton = (Button) findViewById(R.id.button_chef_signup);
        Button yourButton1 = (Button) findViewById(R.id.button_chef_login);
        mauth = FirebaseAuth.getInstance();
        progressDialog = new ProgressBar(this);
        FirebaseUser user = mauth.getCurrentUser();
      /*  try {

        if (user != null) {
           // finish();
            try {

                startActivity(new Intent(this, profile.class));
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), "First logout Customer account", Toast.LENGTH_SHORT).show();
            }

        }
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), " logout Customer account", Toast.LENGTH_SHORT).show();
        }*/

        yourButton1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validate(chef_email.getText().toString(), chef_pass.getText().toString());
                    }
                }
        );
        yourButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(Chef_Signin.this, Chef_signup.class));
                    }
                });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Chef_Signin.this,PasswordActivity.class));
            }
        });
    }
    /*public void btn_login_chef() {
        final String cheff_email = chef_email.getText().toString();
        final String cheff_password = chef_pass.getText().toString();
        try {
            reff.child(cheff_email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cheff chef = dataSnapshot.getValue(cheff.class);
                    if (cheff_password.equals(chef.getPass()) )
                    {
                        Toast.makeText(getApplicationContext(), "Login Success full", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Chef_Signin.this, Khana_Online.class));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Enter correct password", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Id dosent exist", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void validate (String useremail, String userpass)
    {
        mauth.signInWithEmailAndPassword(useremail,userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    //Toast.makeText(Chef_Signin.this," Sucessful ",Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(Chef_Signin.this,profile.class));
                    checkEmailVerification();
                }
                else
                    Toast.makeText(Chef_Signin.this,"faield",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        boolean flag = firebaseUser.isEmailVerified();

        if(flag)
        {
            startActivity(new Intent(Chef_Signin.this, profile.class));
        }
        else
        {
            Toast.makeText(this,"Verify your email", Toast.LENGTH_SHORT).show();
            mauth.signOut();
        }
    }
}