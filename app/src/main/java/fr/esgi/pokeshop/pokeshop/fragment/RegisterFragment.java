package fr.esgi.pokeshop.pokeshop.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import fr.esgi.pokeshop.pokeshop.R;

/**
 * Created by Marion on 11/12/2016.
 */

public class RegisterFragment extends Fragment {
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
        final View view = inflater.inflate(R.layout.fragment_register, container, false);

        editEmail = (EditText) view.findViewById(R.id.email);
        editPwd = (EditText) view.findViewById(R.id.password);
        editfirstName = (EditText) view.findViewById(R.id.firstName);
        editlastName = (EditText) view.findViewById(R.id.lastName);

        registerButton = (Button) view.findViewById(R.id.subscribe_button);
        return view;
    }

}