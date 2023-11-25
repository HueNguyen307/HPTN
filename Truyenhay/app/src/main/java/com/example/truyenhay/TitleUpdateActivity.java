package com.example.truyenhay;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.truyenhay.model.Book;
import com.example.truyenhay.model.Genre;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TitleUpdateActivity extends AppCompatActivity {

    EditText name;
    EditText description;
    EditText author;
    Button btnupdate, btndelete, btnaddchap;

    private Book title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_update);
        name=findViewById(R.id.edtTitleName);
        author=findViewById(R.id.edtTitleAuthor);
        description=findViewById(R.id.edtTitleDes);
        btndelete=findViewById(R.id.TitleDelete);
        btnupdate=findViewById(R.id.TitleUpdate);
        btnaddchap=findViewById(R.id.addChap);
        Intent intent=getIntent();
        title = (Book) intent.getSerializableExtra("item");
        name.setText(title.getName());
        author.setText(title.getAuthor());
        description.setText(title.getDescription());
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = title.getId();
                System.out.println("IDDDDDDDD:"+id);

                RequestQueue queue = Volley.newRequestQueue(TitleUpdateActivity.this);

                String url = "http://192.168.1.14:8000/"+"api/delete_title/";

                Map<String,String> body = new HashMap<>();


                url+="?titleid="+id;
                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                            System.out.println(jsonObject.getString("error"));
                            Toast.makeText(TitleUpdateActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }){
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String, String> getParams(){
                        return body;
                    }

                };
                queue.add(stringRequest);
                Toast.makeText(TitleUpdateActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=name.getText().toString();
                String d=description.getText().toString();
                String a=author.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(TitleUpdateActivity.this);

                String url = "http://192.168.1.14:8000/"+"api/update_title/";

                Map<String,String> body = new HashMap<>();

                body.put("titleid", title.getId().toString());
                body.put("genreId_id",title.getGenreid().toString());
                body.put("name",n);
                body.put("description",d);
                body.put("author",a);

                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TitleUpdateActivity.this, "Update thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                            System.out.println(jsonObject.getString("error"));
                            Toast.makeText(TitleUpdateActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        }
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
                finish();
            }
        });
        btnaddchap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TitleUpdateActivity.this, ChapList.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
    }
}