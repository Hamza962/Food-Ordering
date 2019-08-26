package com.example.khana_online.fyp;

import android.content.Intent;
import android.net.Uri;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class profile extends AppCompatActivity implements Profile_Fragment.OnFragmentInteractionListener,Dishes_Fragment.OnFragmentInteractionListener, Order_Fragment.OnFragmentInteractionListener{

private Button update;
    private FirebaseAuth firebaseAuth;
ImageView ProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TabLayout tablayout =findViewById(R.id.tablayout);
        tablayout.addTab(tablayout.newTab().setText("Profile"));
        tablayout.addTab(tablayout.newTab().setText("Dishes"));
        tablayout.addTab(tablayout.newTab().setText("Orders"));
        final ViewPager viewPager = findViewById(R.id.pager);
        final pageradapter pager = new pageradapter(getSupportFragmentManager(),tablayout.getTabCount());
        viewPager.setAdapter(pager);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));


        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



       /* logout = (Button) findViewById(R.id.btnlogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               firebaseAuth.signOut();
               finish();
                startActivity(new Intent(profile.this,Chef_Signin.class));
            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;  // super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logoutmenu:
            {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(profile.this,Chef_Signin.class));
            }

            case R.id.profilemenu:
            {
                startActivity(new Intent(profile.this,profile.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
