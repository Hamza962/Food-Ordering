package com.example.khana_online.fyp;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {
    private EditText passEmail;
    private Button passResetBtn;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        passEmail = (EditText)findViewById(R.id.EtEmailpass);
        passResetBtn = (Button)findViewById(R.id.btnResetpass);
        firebaseAuth = FirebaseAuth.getInstance();
        passResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = passEmail.getText().toString().trim();
                if(email.equals(""))
                {
                    Toast.makeText(PasswordActivity.this,"Please enter Your Registered Email",Toast.LENGTH_LONG).show();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(PasswordActivity.this,"Password reset Email sent!",Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this,Chef_Signin.class));
                            }
                            else
                            {
                                Toast.makeText(PasswordActivity.this," Error in sending Password reset Email ",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
