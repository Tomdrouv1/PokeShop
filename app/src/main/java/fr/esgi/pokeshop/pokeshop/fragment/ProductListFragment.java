package fr.esgi.pokeshop.pokeshop.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.adapter.ProductAdapter;
import fr.esgi.pokeshop.pokeshop.model.Product;
import fr.esgi.pokeshop.pokeshop.service.ConnectListener;
import fr.esgi.pokeshop.pokeshop.service.WebService;
import fr.esgi.pokeshop.pokeshop.utils.Constant;

/**
 * Created by Marion on 25/12/2016.
 */

public class ProductListFragment extends Fragment {

    private Button addProduct;
    private Product product;
    private List<Product> productList = new ArrayList<Product>();
    private ListView productListView;
    private TextView total;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle args = getArguments();
        int id = args.getInt("listId");

        productListView = (ListView) view.findViewById(R.id.product_list);
        total = (TextView) view.findViewById(R.id.total);

        final WebService asyncTask = new WebService(this.getActivity());
        asyncTask.setListener(new ConnectListener() {
            @Override
            public void onSuccess(JSONObject json) {
                JSONArray list;
                JSONObject item;
                String name;
                Integer quantity;
                Double price;
                ProductAdapter productAdapter;
                int id;
                try {
                    list = json.getJSONArray("result");
                    for (int i = 0; i < list.length(); i++) {
                        item = list.getJSONObject(i);
                        id = Integer.parseInt(item.getString("id"));
                        name = item.getString("name");
                        quantity = item.getInt("quantity");
                        price = item.getDouble("price");

                        product = new Product(id, name, quantity, price);
                        productList.add(product);
                    }
                    productAdapter = new ProductAdapter(getActivity(), productList);
                    productListView.setAdapter(productAdapter);

                    if (productList.size() > 0){
                        total.setText("Prix total : " + countTotal() + " €");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }
        });

        SharedPreferences settings = this.getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        String userToken = settings.getString("user_token", null);
        String url = Constant.LIST_PRODUCT_URL + "?token=" + userToken + "&shopping_list_id=" + id;
        asyncTask.execute(url);

        addProduct = (Button) view.findViewById(R.id.add_product);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = getArguments();
                int id = args.getInt("listId");
                Fragment fragment = new CreateProductFragment();
                FragmentManager fragmentManager;

                fragmentManager = getFragmentManager();
                args.putInt("listId", id);
                fragment.setArguments(args);
                fragmentManager.beginTransaction()
                        .replace(R.id.activity_list, fragment)
                        .commit();
            }
        });
    }

    private double countTotal(){
        double total = 0;
        for(Product product : productList){
            total = total + (product.getPrice() * product.getQuantity());
        }

        return total;
    }
}