package com.example.truyenhay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.truyenhay.model.Chap;

import org.json.JSONException;
import org.json.JSONObject;

public class ChapActivity extends AppCompatActivity {
    private TextView textViewContent,textViewTitle,textViewNameChapter;
    Chap chap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);
        textViewContent=findViewById(R.id.textViewContentChapter);
        textViewNameChapter=findViewById(R.id.textViewNameChapter);
        textViewTitle=findViewById(R.id.textViewNameTitle);

        Intent intent = getIntent();
        chap=(Chap)intent.getSerializableExtra("chap");
        String chapterid=chap.getId();
        String name = chap.getName();

        textViewTitle.setText(name.toUpperCase());
        String url = "http://192.168.1.14:8000/api"+"/get_chapter_info/";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("chapterid", chapterid);
        } catch (JSONException e) {
            System.err.println(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//
                            String titleId = response.getString("titleId");
                            String name = response.getString("name");
                            String number = response.getString("number");
                            String content = response.getString("content");
                            String created_at = response.getString("created_at");
                            String updated_at = response.getString("updated_at");

                            textViewContent.setText(content);
                            textViewNameChapter.setText("Chapter " + number + " : " + name);

                        } catch (JSONException e) {
                            Toast.makeText(ChapActivity.this, "Loi xu li Response", Toast.LENGTH_SHORT).show();
                            System.err.println(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChapActivity.this, "Loi response: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println("error: " + error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ChapActivity.this);
        requestQueue.add(jsonObjectRequest);
    }
    public void getChapter(int chapterid) {


    }
}