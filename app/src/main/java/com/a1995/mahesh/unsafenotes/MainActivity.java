//File name - MainActivity.java
//Author - Mahesh

/* MainActivity is the launcher activity of the app
   It hosts notesFragment */

package com.a1995.mahesh.unsafenotes;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private Fragment mFragment;

// method to get properly configured intent of MainActivity
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);        //inflating toolbar
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);      //inflating DrawerLayout
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open_drawer, R.string.close_drawer);  //creating  a drawer toggle button
        mDrawer.setDrawerListener(mDrawerToggle);           //wiring toggle button with drawer

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);  //inflating navigation view
        setUpDrawerContent(mNavigationView);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);          //this enables hamburger icon in actionbar
        mFragment = NotesFragment.newInstance("work");
        addFragment();                                      //this method places mFragment in fragment container

    }

    //this method creates listener for navigation view items
    private void setUpDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    //this method wires navigation view items with actions on selection
    private void selectDrawerItem(MenuItem item){

    // NotesFragment.newInstance(String category) returns a fragment containing notes of that category
        switch (item.getItemId()){
            case R.id.work_notes:
                mFragment = NotesFragment.newInstance("work");
                break;

            case R.id.personal:
                mFragment = NotesFragment.newInstance("personal");
                break;

            case R.id.poetry:
                mFragment = NotesFragment.newInstance("poetry");
                break;
        }

        addFragment();

        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();

    }



    private void addFragment(){

        //fill the fragment view with the selected fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,mFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}
