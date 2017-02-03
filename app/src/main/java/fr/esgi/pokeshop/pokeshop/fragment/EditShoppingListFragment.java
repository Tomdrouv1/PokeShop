package fr.esgi.pokeshop.pokeshop.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.service.ConnectListener;
import fr.esgi.pokeshop.pokeshop.service.WebService;
import fr.esgi.pokeshop.pokeshop.utils.Constant;

/**
 * Created by Marion on 03/02/2017.
 */

public class EditShoppingListFragment extends Fragment {

    private EditText editName;
    private String name;
    private int id;

    public Button editButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_list, container, false);


        editName = (EditText) view.findViewById(R.id.name);

        Bundle args = getArguments();
        id = args.getInt("listId");
        name = args.getString("listName");

        editName.setText(name);
        editButton = (Button) view.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        return view;
    }

    private void edit() {
        if (!validate()) {
            return;
        }

        name = editName.getText().toString();

        SharedPreferences settings = this.getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        String userToken = settings.getString("user_token", null);
        try {
            name = URLEncoder.encode(name,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = Constant.UPDATE_SHOPPINGLIST_URL + "?token=" + userToken + "&name=" + name + "&id=" + id;

        final WebService asyncTask = new WebService(this.getActivity());
        asyncTask.setListener(new ConnectListener() {
            @Override
            public void onSuccess(JSONObject json) {
                Fragment fragment = new ShoppingListFragment();
                FragmentManager fragmentManager;

                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.activity_list, fragment)
                        .commit();

                Toast.makeText(getActivity(), "Liste modifiée avec succès !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String msg) {
                // TODO: 25/12/2016
                // onLoginFailed();
            }
        });

        asyncTask.execute(url);

    }

    private boolean validate() {
        boolean valid = true;

        name = editName.getText().toString();
        if (name.isEmpty() || name.length() < 3) {
            editName.setError("doit au moins faire plus de 3 caractères");
            valid = false;
        } else {
            editName.setError(null);
        }

        return valid;
    }
}
