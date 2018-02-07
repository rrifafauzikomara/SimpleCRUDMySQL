package com.example.rrifafauzikomara.biodatadiri;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateMakanan extends AppCompatActivity {

    private EditText editTextNama, editTextAsal, editTextHarga, editTextNo;
    private Button buttonUpdate, buttonDelete, buttonBack;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_makanan);

        Intent intent = getIntent();
        id = intent.getStringExtra("user_id");

        editTextNama = (EditText) findViewById(R.id.editTextName);
        editTextAsal = (EditText) findViewById(R.id.editTextAsal);
        editTextHarga = (EditText) findViewById(R.id.editTextHarga);
        editTextNo = (EditText) findViewById(R.id.editTextNo);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        buttonUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (editTextNama.getText().toString().trim().length() == 0
                        || editTextAsal.getText().toString().trim().length() == 0
                        || editTextHarga.getText().toString().trim().length() == 0){
                    Toast.makeText(UpdateMakanan.this, "Data tidak boleh kosong!", Toast.LENGTH_LONG).show();
                } else {
                    updateMakanan();
                    startActivity(new Intent(UpdateMakanan.this, DataMakanan.class));
                    finish();
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                confirmDeleteMakanan();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateMakanan.this, DataMakanan.class));
                finish();
            }
        });
        editTextNo.setText(id);
        getMakanan();
    }
    private void getMakanan(){
        class GetMakanan extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateMakanan.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showMakanan(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://192.168.20.101/web_service/Makanan/get_makanan.php?no=",id);
                return s;
            }
        }
        GetMakanan gm = new GetMakanan();
        gm.execute();
    }
    private void showMakanan(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            String nama_makanan = c.getString("nama_makanan");
            String asal_makanan = c.getString("asal_makanan");
            String harga_makanan = c.getString("harga_makanan");

            editTextNama.setText(nama_makanan);
            editTextAsal.setText(asal_makanan);
            editTextHarga.setText(harga_makanan);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateMakanan(){
        final String nama_makanan = editTextNama.getText().toString();
        final String asal_makanan = editTextAsal.getText().toString();
        final String harga_makanan = editTextHarga.getText().toString();

        class UpdateMakanan1 extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateMakanan.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateMakanan.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("no",id);
                hashMap.put("nama_makanan",nama_makanan);
                hashMap.put("asal_makanan",asal_makanan);
                hashMap.put("harga_makanan",harga_makanan);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest("http://192.168.20.101/web_service/Makanan/update_makanan.php", hashMap);

                return s;
            }
        }

        UpdateMakanan1 um = new UpdateMakanan1();
        um.execute();
    }
    private void deleteMakanan(){
        class DeleteMakanan extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateMakanan.this, "Deleting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateMakanan.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://192.168.20.101/web_service/Makanan/delete_makanan.php?no=", id);
                return s;
            }
        }

        DeleteMakanan dm = new DeleteMakanan();
        dm.execute();
    }

    private void confirmDeleteMakanan(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah anda ingin mengapus data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteMakanan();
                        startActivity(new Intent(UpdateMakanan.this, DataMakanan.class));
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}