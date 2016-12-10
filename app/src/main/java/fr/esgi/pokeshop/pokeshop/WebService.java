package fr.esgi.pokeshop.pokeshop;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import fr.esgi.pokeshop.pokeshop.model.Product;

/**
 * Created by Marion on 02/12/2016.
 */

public class WebService {

    private String url = "http://appspaces.fr/esgi/shopping_list/product/list.php";

    Gson gson;

    public WebService() {
        gson = new Gson();
    }

    private InputStream sendRequest(URL url) throws Exception {

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                urlConnection.connect();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return urlConnection.getInputStream();
                }
            } finally {
                urlConnection.disconnect();
            }
        } catch(Exception e) {
            throw new Exception("");
        }
        return null;
    }

    public List<Product> getProducts() {

        try {
            InputStream inputStream = sendRequest(new URL(url));

            if(inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);

                return gson.fromJson(reader, new TypeToken<List<Product>>(){}.getType());
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
