package com.example.rrifafauzikomara.biodatadiri;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateDataDiri extends AppCompatActivity {

    private EditText editTextId, editTextNama, editTextAlamat, editTextKesan;
    private Button update, hapus;
    private RadioGroup rgJenkel;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_diri);

        Intent intent = getIntent();
        id = intent.getStringExtra("user_id");

        editTextId = (EditText) findViewById(R.id.etIdP);
        editTextNama = (EditText) findViewById(R.id.etNama);
        rgJenkel = (RadioGroup) findViewById(R.id.radioGroup);
        editTextAlamat = (EditText) findViewById(R.id.etAlamat);
        editTextKesan = (EditText) findViewById(R.id.etDes);
        update = (Button) findViewById(R.id.btnUpdate);
        hapus = (Button) findViewById(R.id.btnHapus);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNama.getText().toString().trim().length() == 0
                        || editTextAlamat.getText().toString().trim().length() == 0
                        || editTextKesan.getText().toString().trim().length() == 0) {
                    Toast.makeText(UpdateDataDiri.this, "Data tidak boleh kosong !", Toast.LENGTH_LONG).show();
                } else {
                    updatePesanan();
                    startActivity(new Intent(UpdateDataDiri.this, DataDiri.class));
                    finish();
                }
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeletePesanan();
            }
        });

        editTextId.setText(id);
        getPesanan();
    }

    private void getPesanan(){
        class GetPesanan extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateDataDiri.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showPesanan(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://192.168.20.101/web_service/Kesan/get_kesan.php?id=",id);
                return s;
            }
        }
        GetPesanan gp = new GetPesanan();
        gp.execute();
    }

    private void showPesanan(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            String fullname = c.getString("fullname");
            String address = c.getString("address");
            String kesan = c.getString("kesan");

            editTextNama.setText(fullname);
            editTextAlamat.setText(address);
            editTextKesan.setText(kesan);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updatePesanan(){
        String jk = null;
        switch (rgJenkel.getCheckedRadioButtonId()) {
            case R.id.rbLakilaki:
                jk = "Laki-Laki";
                break;
            case R.id.rbPerempuan:
                jk = "Perempuan";
                break;
        }

        final String fullname = editTextNama.getText().toString();
        final String gender = jk;
        final String address = editTextAlamat.getText().toString();
        final String kesan = editTextKesan.getText().toString();

        class UpdatePesanan extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateDataDiri.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateDataDiri.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("id",id);
                hashMap.put("fullname",fullname);
                hashMap.put("gender",gender);
                hashMap.put("address",address);
                hashMap.put("kesan",kesan);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest("http://192.168.20.101/web_service/Kesan/update_kesan.php", hashMap);

                return s;
            }
        }

        UpdatePesanan up = new UpdatePesanan();
        up.execute();
    }

    private void deletePesanan(){
        class DeletePesanan extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateDataDiri.this, "Deleting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateDataDiri.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://192.168.20.101/web_service/delete_kesan.php?id=", id);
                return s;
            }
        }

        DeletePesanan dp = new DeletePesanan();
        dp.execute();
    }

    private void confirmDeletePesanan(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah anda ingin mengapus data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deletePesanan();
                        startActivity(new Intent(UpdateDataDiri.this, DataDiri.class));
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