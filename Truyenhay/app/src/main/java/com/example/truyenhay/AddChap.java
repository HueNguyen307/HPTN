package com.example.truyenhay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.truyenhay.model.Book;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AddChap extends AppCompatActivity {

    private Button add;
    private EditText num,name,content;
    Book title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chap);
        add=findViewById(R.id.add);
        num=findViewById(R.id.num);
        name=findViewById(R.id.name);
        content=findViewById(R.id.content);
        Intent intent=getIntent();
        title = (Book) intent.getSerializableExtra("title");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=name.getText().toString();
                String no =num.getText().toString();
                String c=content.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(AddChap.this);

                String url = "http://192.168.1.14:8000/"+"api/add_chapter/";

                Map<String,String> body = new HashMap<>();
                body.put("name", n);
                body.put("number",no);
                body.put("content",c);
                body.put("titleid",title.getId());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String,String> getParams(){
                        return body;
                    }

                };
                queue.add(stringRequest);
                Toast.makeText(AddChap.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}