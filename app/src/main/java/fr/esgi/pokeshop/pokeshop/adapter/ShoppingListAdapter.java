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

import java.text.SimpleDateFormat;
import java.util.List;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.fragment.EditShoppingListFragment;
import fr.esgi.pokeshop.pokeshop.fragment.ShoppingListFragment;
import fr.esgi.pokeshop.pokeshop.holder.ShoppingListHolder;
import fr.esgi.pokeshop.pokeshop.model.ShoppingList;
import fr.esgi.pokeshop.pokeshop.service.ConnectListener;
import fr.esgi.pokeshop.pokeshop.service.WebService;
import fr.esgi.pokeshop.pokeshop.utils.Constant;

/**
 * Created by Marion on 25/12/2016.
 */

public class ShoppingListAdapter extends ArrayAdapter<ShoppingList> {

    private Context context = this.getContext();

    public ShoppingListAdapter(Context context, List<ShoppingList> shoppingLists){
        super(context, 0, shoppingLists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shopping_list_row_view, parent, false);
        }

        ShoppingListHolder viewHolder = (ShoppingListHolder) convertView.getTag();

        if(viewHolder == null){
            viewHolder = new ShoppingListHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.shopping_list_name);
            viewHolder.createdDate = (TextView) convertView.findViewById(R.id.shopping_list_created_date);
            viewHolder.deleteList = (Button) convertView.findViewById(R.id.delete_list);
            viewHolder.editList = (Button) convertView.findViewById(R.id.edit_list);
            convertView.setTag(viewHolder);
        }

        final ShoppingList shoppingList = getItem(position);
        viewHolder.name.setText(shoppingList.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        viewHolder.createdDate.setText(dateFormat.format(shoppingList.getCreated_date()));

        final WebService asyncTask = new WebService(context);
        asyncTask.setListener(new ConnectListener() {
            @Override
            public void onSuccess(JSONObject json) {
                Toast.makeText(getContext(), "Supprimé", Toast.LENGTH_LONG).show();
                Fragment fragment = new ShoppingListFragment();
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

        viewHolder.deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
                String userToken = settings.getString("user_token", null);
                if (userToken != null && userToken != "") {
                    String url = Constant.REMOVE_SHOPPINGLIST_URL + "?token=" + userToken + "&id=" + shoppingList.getId();
                    asyncTask.execute(url);
                }
            }
        });

        viewHolder.editList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditShoppingListFragment();
                Bundle args = new Bundle();
                args.putInt("listId", shoppingList.getId());
                args.putString("listName", shoppingList.getName());
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
