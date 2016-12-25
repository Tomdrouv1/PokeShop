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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.esgi.pokeshop.pokeshop.R;
import fr.esgi.pokeshop.pokeshop.adapter.ShoppingListAdapter;
import fr.esgi.pokeshop.pokeshop.model.ShoppingList;
import fr.esgi.pokeshop.pokeshop.service.ConnectListener;
import fr.esgi.pokeshop.pokeshop.service.WebService;
import fr.esgi.pokeshop.pokeshop.utils.Constant;

/**
 * Created by Marion on 25/12/2016.
 */

public class ShoppingListFragment extends Fragment {

    private Button addList;
    private ShoppingList shopingList;
    private List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
    private ListView shoppingListListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shoppingListListView = (ListView) view.findViewById(R.id.shopping_list);

        final WebService asyncTask = new WebService(this.getActivity());
        asyncTask.setListener(new ConnectListener() {
            @Override
            public void onSuccess(JSONObject json) {
                JSONArray list;
                JSONObject item;
                Date date;
                String completed;
                String name;
                ShoppingListAdapter shoppingListAdapter;
                int id;
                try {
                    list = json.getJSONArray("result");
                    for (int i = 0; i < list.length(); i++) {
                        item = list.getJSONObject(i);
                        id = Integer.parseInt(item.getString("id"));
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try{
                            date = simpleDateFormat.parse(item.getString("created_date"));
                        } catch(ParseException e){
                            date = null;
                        }
                        name = item.getString("name");
                        completed = item.getString("completed");

                        if (completed.equals("0")){
                            shopingList = new ShoppingList(id, name, date, false);
                        } else {
                            shopingList = new ShoppingList(id, name, date, true);
                        }
                        shoppingLists.add(shopingList);
                    }
                    shoppingListAdapter = new ShoppingListAdapter(getActivity(), shoppingLists);
                    shoppingListListView.setAdapter(shoppingListAdapter);
                    shoppingListListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
                            ShoppingList item = (ShoppingList) shoppingListListView.getItemAtPosition(position);

                            Fragment fragment = new ProductListFragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            Bundle args = new Bundle();
                            args.putInt("listId", item.getId());
                            fragment.setArguments(args);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.activity_list, fragment)
                                    .commit();
                        }
                    });

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
        String url = Constant.LIST_SHOPPINGLIST_URL + "?token=" + userToken;
        asyncTask.execute(url);

        addList = (Button) view.findViewById(R.id.add_list);
        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CreateShoppingListFragment();
                FragmentManager fragmentManager;

                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.activity_list, fragment)
                        .commit();
            }
        });
    }
}
