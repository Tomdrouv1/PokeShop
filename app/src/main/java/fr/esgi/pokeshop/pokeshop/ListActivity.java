package fr.esgi.pokeshop.pokeshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import fr.esgi.pokeshop.pokeshop.WebService;
import fr.esgi.pokeshop.pokeshop.model.Product;

public class ListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        WebService webservice = new WebService();
        List<Product> list = webservice.getProducts();

        adapter.add("test");

        listView.setAdapter(adapter);
    }
}
