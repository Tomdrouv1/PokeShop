package fr.esgi.pokeshop.pokeshop.activity;

import android.app.Fragment;
import android.app.FragmentManager;
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
import fr.esgi.pokeshop.pokeshop.fragment.SignInFragment;
import fr.esgi.pokeshop.pokeshop.fragment.PokeGridFragment;
import fr.esgi.pokeshop.pokeshop.fragment.PokeListFragment;
import fr.esgi.pokeshop.pokeshop.fragment.SignUpFragment;

public class GridActivity extends AppCompatActivity {

    private ListView mListView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private Button mGridButton;
    private Button mListButton;
    private TextView mListGridTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);


        mListView = (ListView) findViewById(R.id.navigation_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        mGridButton = (Button) findViewById(R.id.to_grid);
        mListButton = (Button) findViewById(R.id.to_list);
        mListGridTitle = (TextView) findViewById(R.id.list_grid_title);

        getFragmentManager().beginTransaction()
            .add(R.id.activity_list, new PokeListFragment())
            .commit();

        addDrawerItems();
        setupDrawer();
        setButtonListener();

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    private void addDrawerItems() {
        String[] navigationArray = {
                "Pokémons",
                "Catégories",
                "S'inscrire",
                "Se connecter"
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
                changeFragment(new PokeGridFragment(), position);
                break;
            case 1:
                Toast.makeText(GridActivity.this, "todo", Toast.LENGTH_LONG).show();
                break;
            case 2:
                changeFragment(new SignUpFragment(), position);
                break;
            case 3:
                changeFragment(new SignInFragment(), position);
            default:
        }
    }

    private void changeFragment(Fragment myfragment, int position) {
        Fragment fragment = myfragment;
        Bundle args;
        FragmentManager fragmentManager;
        args = new Bundle();
        fragment.setArguments(args);

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

    private void setButtonListener() {
        mGridButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_list, new PokeGridFragment())
                        .commit();

                mListGridTitle.setText(R.string.grid_title);
            }
        });

        mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_list, new PokeListFragment())
                        .commit();

                mListGridTitle.setText(R.string.list_title);
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return (mDrawerToggle.onOptionsItemSelected(item)) || super.onOptionsItemSelected(item);
    }
}
