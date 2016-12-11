package fr.esgi.pokeshop.pokeshop.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;


public class WebService extends AsyncTask<String, Void, JSONObject> {

    private Context context;
    private String content;
    private String error;
    private ProgressDialog progressDialog;
    private String data = "";
    private ConnectListener listener;

    public WebService(Context cxt) {
        context = cxt;
        progressDialog = new ProgressDialog(context);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setTitle("Chargement ...");
        progressDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        BufferedReader br = null;

        URL url;
        JSONObject jsonResponse = null;

        try {
            url = new URL(params[0]);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
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
            jsonResponse = new JSONObject(content);

        } catch (JSONException e) {
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
        return jsonResponse;
    }


    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        String msg = null;
        progressDialog.dismiss();
        try {
            if (listener != null && result != null){
                listener.onSuccess(result);
            } else{
               msg = result.getString("msg");
                listener.onFailed(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setListener(ConnectListener listener) {
        this.listener = listener;
    }

    public ConnectListener getListener() {
        return listener;
    }

}
