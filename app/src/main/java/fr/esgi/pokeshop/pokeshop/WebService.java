package fr.esgi.pokeshop.pokeshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * Created by Marion on 02/12/2016.
 */

public class WebService extends AsyncTask<String, Void, Void> {

    private Context context;
    private String content;
    private String error;
    private ProgressDialog progressDialog;
    private String data = "";
    private TextView showReceivedData;
    private TextView showParsedJSON;
    private String token = UUID.randomUUID().toString();

    public WebService(Context cxt, TextView receivedData, TextView parsedData) {
        context = cxt;
        progressDialog = new ProgressDialog(context);
        showReceivedData = receivedData;
        showParsedJSON = parsedData;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setTitle("Chargement ...");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... params) {
        BufferedReader br = null;

        URL url;
        try {
            url = new URL(params[0]);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Token token=" + token);
            connection.setDoOutput(true);

            OutputStreamWriter outputStreamWr = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWr.write(data);
            outputStreamWr.flush();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = br.readLine())!=null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }

            content = sb.toString();

        } catch (MalformedURLException e) {
            error = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            error = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        progressDialog.dismiss();

        if(error!=null) {
            showReceivedData.setText("Error " + error);
        } else {
            showReceivedData.setText(content);

//            String output = "";
//            JSONObject jsonResponse;
//
//            try {
//                jsonResponse = new JSONObject(content);
//
//                JSONArray jsonArray = jsonResponse.optJSONArray("results");
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject child = jsonArray.getJSONObject(i);
//
//                    String name = child.getString("name");
//
//                    output += name + System.getProperty("line.separator");
//                    output += System.getProperty("line.separator");
//
//                }
//
//                showParsedJSON.setText(output);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }
    }


}
