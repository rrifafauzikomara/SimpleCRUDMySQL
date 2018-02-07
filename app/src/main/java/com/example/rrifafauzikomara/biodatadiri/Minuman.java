package com.example.rrifafauzikomara.biodatadiri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class Minuman extends AppCompatActivity {

    private EditText editTextName, editTextPeru, editTextNetto, editTextPesan;
    private RadioGroup rgSedotan, rgTempat;
    private Button buttonAdd, buttonView, buttonBack;
    private static final String REGISTER_URL = "http://192.168.20.101/web_service/Minuman/minuman.php";
    String nama_minuman, perusahaan, netto, sedotan, tempat, nama_pemesan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minuman);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPeru = (EditText) findViewById(R.id.editTextPeru);
        editTextNetto = (EditText) findViewById(R.id.editTextNetto);
        editTextPesan = (EditText) findViewById(R.id.editTextPesan);
        rgSedotan = (RadioGroup) findViewById(R.id.radioGroup1);
        rgTempat = (RadioGroup) findViewById(R.id.radioGroup2);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sd = null;
                switch (rgSedotan.getCheckedRadioButtonId()){
                    case R.id.rbYes:
                        sd = "Yes";
                        break;
                    case R.id.rbNo:
                        sd = "No";
                        break;
                }
                String tp = null;
                switch (rgTempat.getCheckedRadioButtonId()){
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
                nama_minuman = editTextName.getText().toString();
                perusahaan = editTextPeru.getText().toString();
                netto = editTextNetto.getText().toString();
                sedotan = sd;
                tempat = tp;
                nama_pemesan = editTextPesan.getText().toString();

                minuman(nama_minuman, perusahaan, netto, sedotan, tempat, nama_pemesan);
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Minuman.this, DataMinuman.class));
                finish();
            }
        });

    }
    private void minuman(String nama_minuman, String perusahaan, String netto, String sedotan, String tempat, String nama_pemesan) {
        class AddMinuman extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler requestHandler = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Minuman.this, "Please Wait", "Loading...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (editTextName.getText().toString().trim().length() == 0
                        || editTextPeru.getText().toString().trim().length() == 0
                        || editTextNetto.getText().toString().trim().length() == 0
                        || editTextPesan.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(Minuman.this, DataMinuman.class);
                    finish();
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("nama_minuman",params[0]);
                data.put("perusahaan",params[1]);
                data.put("netto",params[2]);
                data.put("sedotan",params[3]);
                data.put("tempat",params[4]);
                data.put("nama_pemesan",params[5]);

                String result = requestHandler.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        AddMinuman am = new AddMinuman();
        am.execute(nama_minuman, perusahaan, netto, sedotan, tempat, nama_pemesan);
    }
}