package fr.esgi.pokeshop.pokeshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import fr.esgi.pokeshop.pokeshop.fragment.PokeGridFragment;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        getFragmentManager().beginTransaction()
            .add(R.id.fragment_grid, new PokeGridFragment())
            .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
