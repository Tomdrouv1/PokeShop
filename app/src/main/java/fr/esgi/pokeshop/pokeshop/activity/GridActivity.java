package fr.esgi.pokeshop.pokeshop.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.fragment.ShoppingListFragment;
import fr.esgi.pokeshop.pokeshop.fragment.PokeGridFragment;
import fr.esgi.pokeshop.pokeshop.fragment.PokeListFragment;
import fr.esgi.pokeshop.pokeshop.fragment.SignInFragment;

public class GridActivity extends AppCompatActivity {

    private ListView mListView;
    public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        SharedPreferences settings = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean isConnected = settings.getBoolean("is_connected", false);
        mListView = (ListView) findViewById(R.id.navigation_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();


        setupDrawer();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        if(isConnected){
            addDrawerItems();
            getFragmentManager().beginTransaction()
                    .add(R.id.activity_list, new ShoppingListFragment())
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .add(R.id.activity_list, new SignInFragment())
                    .commit();
        }
    }

    public void addDrawerItems() {
        String[] navigationArray = {
                "Mes listes",
                "Déconnexion"
        };
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, navigationArray);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }

    private void selectItem(int position) {
        switch(position) {
            case 0:
                changeFragment(new ShoppingListFragment(), position);
                break;
            case 1:
                SharedPreferences settings = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
                settings.edit().clear().apply();
                changeFragment(new SignInFragment(), position);
                Toast.makeText(GridActivity.this, "Déconnecté !", Toast.LENGTH_LONG).show();
                break;
            default:
        }
    }

    private void changeFragment(Fragment myfragment, int position) {
        Fragment fragment = myfragment;
        FragmentManager fragmentManager;

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_list, fragment)
                .commit();

        mListView.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mListView);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Navigation");
                }

                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mActivityTitle);
                }

                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }


    public void setDrawerState(boolean isEnabled) {
        if ( isEnabled ) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mDrawerToggle.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerToggle.syncState();

        }
        else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mDrawerToggle.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerToggle.syncState();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return (mDrawerToggle.onOptionsItemSelected(item)) || super.onOptionsItemSelected(item);
    }
}
