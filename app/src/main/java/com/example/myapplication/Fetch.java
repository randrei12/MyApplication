package com.example.myapplication;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Fetch extends AsyncTask<Void, Void, String> {

        public String plainUrl;
        public LinearLayout layout;
        public Fetch(String url, LinearLayout layout) {
            this.plainUrl = url;
            this.layout = layout;
        }

    protected String doInBackground(Void ...voids) {
        try {
            URL url = new URL(plainUrl);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();
                return response.toString();
            } else return "Request failed with response code: " + responseCode;
        } catch (IOException e) {
            e.printStackTrace();
            return "Request failed: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = "Name: " + jsonObject.getString("title");
                String platforms = "Platforms: " + jsonObject.getJSONArray("platforms").join(",");
                String created = "Created: " + jsonObject.getString("created");

                View complexElementView = LayoutInflater.from(layout.getContext()).inflate(R.layout.card_layout, null);
                ImageView imageView = complexElementView.findViewById(R.id.imageView);
                TextView textView1 = complexElementView.findViewById(R.id.textView1);
                TextView textView2 = complexElementView.findViewById(R.id.textView2);
                TextView textView3 = complexElementView.findViewById(R.id.textView3);

                Glide.with(layout.getContext()).load("http://192.168.1.216:8080/assets/icon.png").into(imageView);
                textView1.setText(title);
                textView2.setText(platforms);
                textView3.setText(created);

                layout.addView(complexElementView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
