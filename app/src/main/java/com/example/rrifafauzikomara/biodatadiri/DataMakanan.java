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

public class DataMakanan extends AppCompatActivity {

    private Button buttonAdd;
    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_makanan);

        buttonAdd = (Button) findViewById(R.id.btnTambah);
        listView = (ListView) findViewById(R.id.listView3);

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentb = new Intent(DataMakanan.this, MainActivity.class);
                finish();
                startActivity(intentb);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(DataMakanan.this, UpdateMakanan.class);
                HashMap<String, String> map = (HashMap)parent.getItemAtPosition(position);
                String UserId = map.get("no").toString();
                intent.putExtra("user_id", UserId);
                startActivity(intent);
                finish();
            }
        });
        getJSON();
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(DataMakanan.this, "Fetching Data", "Wait...",false,false);

            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showMakanan();
            }
            @Override
            protected String doInBackground(Void... params){
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest("http://192.168.20.101/web_service/Makanan/get_all_makanan.php");
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void showMakanan(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String no = jo.getString("no");
                String nama_makanan = jo.getString("nama_makanan");

                HashMap<String, String> users = new HashMap();
                users.put("no", no);
                users.put("nama_makanan", nama_makanan);
                list.add(users);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                DataMakanan.this, list, R.layout.activity_list_item,
                new String[]{"no","nama_makanan"},
                new int[]{R.id.id, R.id.name}
        );
        listView.setAdapter(adapter);
    }
    public void cancel (View view) {
        Intent intent = new Intent(DataMakanan.this, MenuUtama.class);
        startActivity(intent);
    }
}
