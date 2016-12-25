package fr.esgi.pokeshop.pokeshop.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
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
 * Created by Marion on 25/12/2016.
 */

public class CreateShoppingListFragment extends Fragment {

    private EditText editName;
    private String name;

    public Button createButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_list, container, false);

        editName = (EditText) view.findViewById(R.id.name);

        createButton = (Button) view.findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });

        return view;
    }

    private void create() {
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
        String url = Constant.CREATE_SHOPPINGLIST_URL + "?token=" + userToken + "&name=" + name;

        final WebService asyncTask = new WebService(this.getActivity());
        asyncTask.setListener(new ConnectListener() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    JSONObject resultJSON = json.getJSONObject("result");

                    Toast.makeText(getActivity(), "Liste " + resultJSON.getString("name") + " crée !", Toast.LENGTH_SHORT).show();

                    Fragment fragment = new ShoppingListFragment();
                    FragmentManager fragmentManager;

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.activity_list, fragment)
                            .commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String msg) {
//                progressDialog.dismiss();
//                onLoginFailed();
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
