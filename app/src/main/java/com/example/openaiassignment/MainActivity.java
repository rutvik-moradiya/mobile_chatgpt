package com.example.openaiassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = findViewById(R.id.response);
        EditText prompt = findViewById(R.id.enterPrompt);
        Button getAns = findViewById(R.id.button);
        getAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataFromApi api = new fetchDataFromApi();
                String res = "";
                try {
                    res = api.execute(prompt.getText().toString()).get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    JSONObject json = new JSONObject(res);
                    JSONArray ans = json.getJSONArray("choices");
                    text.setText(ans.getJSONObject(0).get("text").toString());
                    Log.d("TAG", "onCreate: in try catch "+ans.getJSONObject(0).get("text"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
//        final String[] thePrompt = {""};
//        prompt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                thePrompt[0] = s.toString();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }
}