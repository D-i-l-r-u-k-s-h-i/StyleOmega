package com.example.dilrukshiperera.styleomega;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dilrukshiperera.styleomega.SupportClasses.SaveSharedPreferenceInstance;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_profile:
                FragmentTransaction ft=clearBackStack();
                       ft.replace(R.id.fragment_container,new ProfileFragment(),"profile").addToBackStack("home").commit();
                break;
            case R.id.nav_cart:
                FragmentTransaction ft2=clearBackStack();
                        ft2.replace(R.id.fragment_container,new CartFragment(),"cart").addToBackStack(null).commit();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment(),"home")
                        .addToBackStack(null).commit();
                break;
            case R.id.nav_contactus:
                FragmentTransaction ft3=clearBackStack();
                        ft3.replace(R.id.fragment_container,new ContactUsFragment(),"contactus").addToBackStack(null).commit();
                break;
            case R.id.nav_signout:
                //clear shared preference
                SaveSharedPreferenceInstance.clearUser(this);
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                Toast.makeText(this,"Signed out.",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment())
                        .addToBackStack(null).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public FragmentTransaction clearBackStack(){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();

        int backstackEntry=getSupportFragmentManager().getBackStackEntryCount();
        List<Fragment> fragmentList=getSupportFragmentManager().getFragments();
        //get the fragment's tag and clear all the other fragments in the stack upto 'home'
        if(backstackEntry>0){
            for(int i=0;i<backstackEntry;i++){
                getSupportFragmentManager().popBackStackImmediate();
                if(fragmentList.size()<i){
                    Fragment fragment=fragmentList.get(0);
                    ft.remove(fragment);

                    if(fragment.getTag()!=null && !(fragment.getTag().toString()).equals("home"))
                        break;
                }
                fragmentList=getSupportFragmentManager().getFragments();
            }
        }
        return ft;
    }
}
