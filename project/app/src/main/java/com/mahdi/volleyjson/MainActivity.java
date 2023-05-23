package com.mahdi.volleyjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mahdi.volleyjson.adapter.CustomAdapter;
import com.mahdi.volleyjson.model.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Model> list;
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private static String JSON_URL = "https://raw.githubusercontent.com/mehdisahraeei/usejson-retrofit/master/file.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rec1);
        list = new ArrayList<>();

        doing();
    }


    private void doing() {

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject listObject = response.getJSONObject(i);

                        Model listModel = new Model();
                        listModel.setId(listObject.getString("id"));
                        listModel.setName(listObject.getString("name"));
                        listModel.setImage(listObject.getString("image"));
                        list.add(listModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new CustomAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(adapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "there are some wrong", Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(jsonArrayRequest);


    }


}
