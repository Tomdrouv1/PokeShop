package fr.esgi.pokeshop.pokeshop.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.esgi.pokeshop.pokeshop.R;

/**
 * Created by Marion on 11/12/2016.
 */

public class SignUpFragment extends Fragment {
    public static String email;
    public static String password;
    public static String firstname;
    public static String lastname;

    private EditText editEmail;
    private EditText editPwd;
    private EditText editfirstName;
    private EditText editlastName;
    public Button registerButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        editEmail = (EditText) view.findViewById(R.id.email);
        editPwd = (EditText) view.findViewById(R.id.password);
        editfirstName = (EditText) view.findViewById(R.id.firstName);
        editlastName = (EditText) view.findViewById(R.id.lastName);

        registerButton = (Button) view.findViewById(R.id.subscribe_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        return view;
    }

    public void signUp() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        registerButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Création du compte...");
        progressDialog.show();

        firstname = editfirstName.getText().toString();
        lastname = editlastName.getText().toString();
        email = editEmail.getText().toString();
        password = editPwd.getText().toString();

        // TODO: Inscription avec webservice

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
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

    public void onSignupSuccess() {
        Toast.makeText(getActivity(), "Succès !", Toast.LENGTH_LONG).show();
        registerButton.setEnabled(true);
        // aller au prochain fragment
    }

    public void onSignupFailed() {
        Toast.makeText(getActivity(), "Il y a des erreurs dans le formulaire", Toast.LENGTH_LONG).show();
        registerButton.setEnabled(true);
    }

}