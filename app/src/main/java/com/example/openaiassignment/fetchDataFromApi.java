package com.example.openaiassignment;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;

public class fetchDataFromApi extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try{
            URL url = new URL("https://api.openai.com/v1/completions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("model","ada:ft-personal-2023-05-04-22-33-10");
//            connection.setRequestProperty("prompt", "When is it legal to cross a bus lane to make a right turn?");
//            connection.setRequestProperty("temperature", String.valueOf(0.6));
//            connection.setRequestProperty("max_tokens", String.valueOf(50));
            connection.setRequestProperty("Authorization", "Bearer 'key'");
            connection.setRequestMethod("POST");

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "ada:ft-personal-2023-05-04-22-33-10");
            requestBody.put("prompt", strings[0]);
            requestBody.put("temperature", 0.1);
            requestBody.put("max_tokens", 50);

            OutputStream outputStream = connection.getOutputStream();
            byte[] requestBodyBytes = requestBody.toString().getBytes("utf-8");
            outputStream.write(requestBodyBytes, 0, requestBodyBytes.length);
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseBuilder.append(line);
            }
            response = responseBuilder.toString();

            int responseCode = connection.getResponseCode();
            Log.i("TAG", "Server responded with: " + responseCode+" response: "+response );
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.d("TAG", "doInBackground: reponse got ");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("TAG", "postExecute: "+s);
    }
}
