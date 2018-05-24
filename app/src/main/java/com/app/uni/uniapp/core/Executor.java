package com.app.uni.uniapp.core;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Sathveegan on 23-May-18.
 */

public class Executor extends AsyncTask<String, Void, String> {

    private String iURL;
    private String card_number;
    private String cvv;
    private String expiry_date;
    private Callback callback;

    public Executor(String URL, String card_number, String cvv, String expiry_date, Callback cb){
        this.iURL = URL;
        this.card_number = card_number;
        this.expiry_date = expiry_date;
        this.cvv = cvv;
        this.callback = cb;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL(iURL);

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("cardNo", card_number);
            postDataParams.put("cvv", cvv);
            postDataParams.put("expiaryDate", expiry_date);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStreamWriter out = new   OutputStreamWriter(connection.getOutputStream());
            out.write(postDataParams.toString());
            out.flush();
            out.close();

            int responseCode=connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {

                    sb.append(line+"\n");

                }

                in.close();
                return sb.toString();

            }
            else {
                return new String("false : "+responseCode);
            }


        } catch (MalformedURLException e) {
            return new String("Exception: " + e.getMessage());
        } catch (IOException e) {
            return new String("Exception: " + e.getMessage());
        } catch (JSONException e) {
            return new String("Exception: " + e.getMessage());
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Log.v("Result", s);
        callback.onCallbackCompleted(s+"");
    }

}
