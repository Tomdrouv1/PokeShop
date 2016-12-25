package fr.esgi.pokeshop.pokeshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.fragment.SignInFragment;
import fr.esgi.pokeshop.pokeshop.fragment.SignUpFragment;

/**
 * Created by Marion on 25/12/2016.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getFragmentManager().beginTransaction()
                .add(R.id.form_container, new SignInFragment())
                .commit();
    }


}
