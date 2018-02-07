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

public class UpdateMinuman extends AppCompatActivity {

    private EditText editTextNo, editTextName, editTextPeru, editTextNetto, editTextPesan;
    private Button buttonUpdate, buttonDelete, buttonBack;
    private RadioGroup rgSedotan, rgTempat;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_minuman);

        Intent intent = getIntent();
        id = intent.getStringExtra("user_id");

        editTextNo = (EditText) findViewById(R.id.editTextNo);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPeru = (EditText) findViewById(R.id.editTextPeru);
        editTextNetto = (EditText) findViewById(R.id.editTextNetto);
        editTextPesan = (EditText) findViewById(R.id.editTextPesan);
        rgSedotan = (RadioGroup) findViewById(R.id.radioGroup1);
        rgTempat = (RadioGroup) findViewById(R.id.radioGroup2);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().toString().trim().length() == 0
                        || editTextPeru.getText().toString().trim().length() == 0
                        || editTextNetto.getText().toString().trim().length() == 0
                        || editTextPesan.getText().toString().trim().length() == 0) {
                    Toast.makeText(UpdateMinuman.this, "Data tidak boleh kosong !", Toast.LENGTH_LONG).show();
                } else {
                    updateMinuman();
                    startActivity(new Intent(UpdateMinuman.this, DataMinuman.class));
                    finish();
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteMinuman();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateMinuman.this, DataMinuman.class));
                finish();
            }
        });
        editTextNo.setText(id);
        getMinuman();
    }
    private void getMinuman(){
        class GetMinuman extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateMinuman.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showMinuman(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://192.168.20.101/web_service/Minuman/get_minuman.php?no=",id);
                return s;
            }
        }
        GetMinuman gm = new GetMinuman();
        gm.execute();
    }
    private void showMinuman(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            String nama_minuman = c.getString("nama_minuman");
            String perusahaan = c.getString("perusahaan");
            String netto = c.getString("netto");
            String nama_pemesan = c.getString("nama_pemesan");

            editTextName.setText(nama_minuman);
            editTextPeru.setText(perusahaan);
            editTextNetto.setText(netto);
            editTextPesan.setText(nama_pemesan);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateMinuman(){
        String sd = null;
        switch (rgSedotan.getCheckedRadioButtonId()) {
            case R.id.rbYes:
                sd = "Yes";
                break;
            case R.id.rbNo:
                sd = "No";
                break;
        }
        String tp = null;
        switch (rgTempat.getCheckedRadioButtonId()) {
            case R.id.rbGelas:
                tp = "Gelas";
                break;
            case R.id.rbCup:
                tp = "Cup";
                break;
            case R.id.rbBottle:
                tp = "Bottle";
                break;
        }

        final String nama_minuman = editTextName.getText().toString();
        final String perusahaan = editTextPeru.getText().toString();
        final String netto = editTextNetto.getText().toString();
        final String sedotan = sd;
        final String tempat = tp;
        final String nama_pemesan = editTextPesan.getText().toString();

        class UpMinuman extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateMinuman.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateMinuman.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("no",id);
                hashMap.put("nama_minuman",nama_minuman);
                hashMap.put("perusahaan",perusahaan);
                hashMap.put("netto",netto);
                hashMap.put("sedotan",sedotan);
                hashMap.put("tempat",tempat);
                hashMap.put("nama_pemesan", nama_pemesan);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest("http://192.168.20.101/web_service/Minuman/update_minuman.php", hashMap);

                return s;
            }
        }

        UpMinuman up = new UpMinuman();
        up.execute();
    }
    private void deleteMinuman(){
        class DeleteMinuman extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateMinuman.this, "Deleting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateMinuman.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://192.168.20.101/web_service/Minuman/delete_minuman.php?no=", id);
                return s;
            }
        }

        DeleteMinuman dm = new DeleteMinuman();
        dm.execute();
    }

    private void confirmDeleteMinuman(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah anda ingin mengapus data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteMinuman();
                        startActivity(new Intent(UpdateMinuman.this, DataMinuman.class));
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