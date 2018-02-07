package com.example.rrifafauzikomara.biodatadiri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DataDiri extends AppCompatActivity {

    private Button buttonTambah;
    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_diri);

        buttonTambah = (Button) findViewById(R.id.btnTambah);
        listView = (ListView)findViewById(R.id.listView3);

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iau = new Intent(DataDiri.this, MainActivity.class);
                finish();
                startActivity(iau);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DataDiri.this, UpdateDataDiri.class);
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                String UserId = map.get("id").toString();
                intent.putExtra("user_id",UserId);
                startActivity(intent);
                finish();
            }
        });
        getJSON();

    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DataDiri.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showPemesanan();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest("http://192.168.20.101/web_service/Kesan/get_all_kesan.php");
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showPemesanan(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString("id");
                String name = jo.getString("fullname");

                HashMap<String,String> users = new HashMap();
                users.put("id", id);
                users.put("fullname", name);
                list.add(users);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                DataDiri.this, list, R.layout.activity_list_item,
                new String[]{"id","fullname"},
                new int[]{R.id.id, R.id.name});
        listView.setAdapter(adapter);
    }

    public void cancel (View view) {
        Intent intent = new Intent(DataDiri.this, MenuUtama.class);
        startActivity(intent);
    }
}
