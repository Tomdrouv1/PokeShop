package fr.esgi.pokeshop.pokeshop.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.service.ConnectListener;
import fr.esgi.pokeshop.pokeshop.service.WebService;
import fr.esgi.pokeshop.pokeshop.utils.Constant;

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

        String url = Constant.WS_SIGNUP_URL+"?email="+email+"&password="+password+"&firstname="+firstname+"&lastname="+lastname;

        final WebService asyncTask = new WebService(this.getActivity());
        asyncTask.setListener(new ConnectListener() {
            @Override
            public void onSuccess(JSONObject json) {
                SharedPreferences sharedPreference = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreference.edit();

                try {
                    JSONObject resultJSON = json.getJSONObject("result");
                    editor.putString("first_name", resultJSON.getString("firstname"));
                    editor.putString("last_name", resultJSON.getString("lastname"));
                    editor.putString("email", resultJSON.getString("email"));
                    editor.putString("user_token", resultJSON.getString("token"));
                    editor.putBoolean("is_connected", true);
                    editor.apply();

                    progressDialog.dismiss();
                    onSignupSuccess();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String msg) {
                onSignupFailed();
            }
        });

        asyncTask.execute(url);
    }

    public void onSignupSuccess() {
        Toast.makeText(getActivity(), "Enregistré et connecté avec succès !", Toast.LENGTH_LONG).show();
        registerButton.setEnabled(true);

        Fragment fragment = new PokeGridFragment();
        FragmentManager fragmentManager;

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_list, fragment)
                .commit();
    }

    public void onSignupFailed() {
        Toast.makeText(getActivity(), "Il y a des erreurs dans le formulaire", Toast.LENGTH_LONG).show();
        registerButton.setEnabled(true);
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