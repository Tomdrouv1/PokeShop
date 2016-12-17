package fr.esgi.pokeshop.pokeshop.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.esgi.pokeshop.pokeshop.R;

/**
 * Created by Marion on 17/12/2016.
 */

public class SignInFragment extends Fragment {

    public static String email;
    public static String password;

    private EditText editEmail;
    private EditText editPwd;
    public Button loginButton;
    public TextView signUpLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        editEmail = (EditText) view.findViewById(R.id.email);
        editPwd = (EditText) view.findViewById(R.id.password);

        loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signUpLink = (TextView) view.findViewById(R.id.link_signup);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SignUpFragment();
                Bundle args;
                FragmentManager fragmentManager;
                args = new Bundle();
                fragment.setArguments(args);

                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.activity_list, fragment)
                        .commit();
            }
        });

        return view;
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authentification...");
        progressDialog.show();

        email = editEmail.getText().toString();
        password = editPwd.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        // stocker le token et aller au prochain fragment
    }

    public void onLoginFailed() {
        Toast.makeText(getActivity(), "Erreur de connexion", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = editEmail.getText().toString();
        String password = editPwd.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Veuillez saisir une adresse email valide");
            valid = false;
        } else {
            editEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editPwd.setError("doit containir entre 4 et 10 caractères");
            valid = false;
        } else {
            editPwd.setError(null);
        }

        return valid;
    }
}
