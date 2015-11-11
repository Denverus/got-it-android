package org.coursera.capstone.gotit.client.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.NavUtils;
import org.coursera.capstone.gotit.client.NavigationViewManager;
import org.coursera.capstone.gotit.client.fragments.CheckInListFragment;
import org.coursera.capstone.gotit.client.fragments.FeedListFragment;
import org.coursera.capstone.gotit.client.fragments.NotificationListFragment;
import org.coursera.capstone.gotit.client.fragments.TeenListFragment;
import org.coursera.capstone.gotit.client.model.Person;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationViewManager mNavigationViewManager;
    private TextView mUsernameTextView;
    private FloatingActionButton mActionButton;
    private boolean mPersonIsTeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.coursera.capstone.gotit.client.R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(org.coursera.capstone.gotit.client.R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("AAA");

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(org.coursera.capstone.gotit.client.R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(org.coursera.capstone.gotit.client.R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(org.coursera.capstone.gotit.client.R.id.navigation_drawer);
        mNavigationViewManager = new NavigationViewManager(this, (DrawerLayout) findViewById(org.coursera.capstone.gotit.client.R.id.drawer_layout), navigationView);
        mUsernameTextView = (TextView) findViewById(org.coursera.capstone.gotit.client.R.id.username);

        updateDrawer();

        mActionButton = (FloatingActionButton) findViewById(org.coursera.capstone.gotit.client.R.id.button_action);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.showCreateCheckIn(MainActivity.this);
            }
        });


        Fragment fragment = AppApplication.getContext().getFragment();

        if (fragment == null) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .add(org.coursera.capstone.gotit.client.R.id.content_frame, new TeenListFragment())
                    .commit();
        } else {
            setFragment(fragment);
        }
    }

    public void updateDrawer() {
        Person person = AppApplication.getContext().getCurrentUser();
        mPersonIsTeen = person.isHasDiabetes();
        mNavigationViewManager.showTeenMenu(mPersonIsTeen);
        mUsernameTextView.setText(person.getName());
    }

    public void setFragment(Fragment fragment) {
        if (!mPersonIsTeen) {
            hideActionButton();
        } else {
            if ((fragment instanceof TeenListFragment) ||
                    (fragment instanceof CheckInListFragment) ||
                    (fragment instanceof FeedListFragment) ||
                    (fragment instanceof NotificationListFragment)) {
                showActionButton();
            } else {
                hideActionButton();
            }
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(org.coursera.capstone.gotit.client.R.id.content_frame, fragment)
                .addToBackStack(fragment.toString())
                .commit();
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

    public void showActionButton() {
        mActionButton.show();
    }

    public void hideActionButton() {
        mActionButton.hide();
    }
}