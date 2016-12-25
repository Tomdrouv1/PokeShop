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
 * Created by Marion on 25/12/2016.
 */

public class CreateProductFragment extends Fragment {

    private EditText editName;
    private EditText editQuantity;
    private EditText editPrice;
    private String name;
    private String quantity;
    private String price;

    public Button createButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_product, container, false);


        editName = (EditText) view.findViewById(R.id.name);
        editQuantity = (EditText) view.findViewById(R.id.quantity);
        editPrice = (EditText) view.findViewById(R.id.price);

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

        Bundle args = getArguments();
        int id = args.getInt("listId");

        name = editName.getText().toString();
        Integer qty = Integer.parseInt(editQuantity.getText().toString());
        Double price = Double.parseDouble(editPrice.getText().toString());

        SharedPreferences settings = this.getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        String userToken = settings.getString("user_token", null);
        try {
            name = URLEncoder.encode(name,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = Constant.CREATE_PRODUCT_URL + "?token=" + userToken + "&shopping_list_id=" + id + "&name=" + name + "&quantity=" + qty + "&price=" + price;

        final WebService asyncTask = new WebService(this.getActivity());
        asyncTask.setListener(new ConnectListener() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    JSONObject resultJSON = json.getJSONObject("result");

                    Toast.makeText(getActivity(), "Produit " + resultJSON.getString("name") + " crée !", Toast.LENGTH_SHORT).show();

                    Bundle args = getArguments();
                    int id = args.getInt("listId");
                    Fragment fragment = new ProductListFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    args.putInt("listId", id);
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction()
                            .replace(R.id.activity_list, fragment)
                            .commit();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

        quantity = editQuantity.getText().toString();
        if (quantity.isEmpty()) {
            editQuantity.setError("choisir une quantité");
            valid = false;
        } else {
            editQuantity.setError(null);
        }

        price = editPrice.getText().toString();
        if (price.isEmpty()) {
            editPrice.setError("choisir un prix");
            valid = false;
        } else {
            editPrice.setError(null);
        }

        return valid;
    }
}
