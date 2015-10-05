package com.jzson.gotit.client.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jzson.gotit.client.NavigationViewManager;
import com.jzson.gotit.client.R;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationViewManager mNavigationViewManager;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("AAA");

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.abc_ic_menu_copy_mtrl_am_alpha);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        mNavigationViewManager = new NavigationViewManager(this, (DrawerLayout) findViewById(R.id.drawer_layout), navigationView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                Toast.makeText(MainActivity.this, ""+counter, Toast.LENGTH_SHORT).show();
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });



/*
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content_frame,  new EventListFragment())
                .commit();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

//        getMenuInflater().inflate(R.menu.main, menu);
//        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
/*
            case R.id.invite_person:
                NavUtils.showInvitePersonActivity(this);
                break;

            case R.id.add_event:
                NavUtils.showCreateEventFragmentActivity(this);
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }
}