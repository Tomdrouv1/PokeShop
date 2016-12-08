package fr.esgi.pokeshop.pokeshop.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.fragment.PokeListFragment;


public class ListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_list);

        getFragmentManager().beginTransaction()
            .add(R.id.fragment_list, new PokeListFragment())
            .commit();
    }
}
