package fr.esgi.pokeshop.pokeshop.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.fragment.EditProductFragment;
import fr.esgi.pokeshop.pokeshop.fragment.ProductListFragment;
import fr.esgi.pokeshop.pokeshop.holder.ProductHolder;
import fr.esgi.pokeshop.pokeshop.model.Product;
import fr.esgi.pokeshop.pokeshop.service.ConnectListener;
import fr.esgi.pokeshop.pokeshop.service.WebService;
import fr.esgi.pokeshop.pokeshop.utils.Constant;

/**
 * Created by Marion on 25/12/2016.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context = this.getContext();

    public ProductAdapter(Context context, List<Product> productList){
        super(context, 0, productList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_row_view, parent, false);
        }

        ProductHolder viewHolder = (ProductHolder) convertView.getTag();

        if(viewHolder == null){
            viewHolder = new ProductHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.product_name);
            viewHolder.quantity = (TextView) convertView.findViewById(R.id.quantity);
            viewHolder.deleteProduct = (Button) convertView.findViewById(R.id.delete_product);
            viewHolder.editProduct = (Button) convertView.findViewById(R.id.edit_product);
            convertView.setTag(viewHolder);
        }
        final Product product = getItem(position);
        viewHolder.name.setText(product.getName());
        viewHolder.quantity.setText("X " + product.getQuantity());

        final WebService asyncTask = new WebService(context);
        asyncTask.setListener(new ConnectListener() {
            @Override
            public void onSuccess(JSONObject json) {
                Toast.makeText(getContext(), "Supprimé", Toast.LENGTH_LONG).show();
                // TODO: 25/12/2016  rediriger vers la liste des produits
                Fragment fragment = new ProductListFragment();
                FragmentManager fragmentManager;

                fragmentManager = ((Activity) context).getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.activity_list, fragment)
                        .commit();
            }

            @Override
            public void onFailed(String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
                String userToken = settings.getString("user_token", null);
                if (userToken != null && userToken != "") {
                    String url = Constant.REMOVE_PRODUCT_URL + "?token=" + userToken + "&id=" + product.getId();
                    asyncTask.execute(url);
                }
            }
        });

        viewHolder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new EditProductFragment();
                Bundle args = new Bundle();
                args.putInt("productId", product.getId());
                args.putInt("listId", product.getShoppingListId());
                args.putString("productName", product.getName());
                args.putInt("productQuantity", product.getQuantity());
                args.putDouble("productPrice", product.getPrice());
                fragment.setArguments(args);

                FragmentManager fragmentManager;

                fragmentManager = ((Activity) getContext()).getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.activity_list, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return convertView;
    }
}
