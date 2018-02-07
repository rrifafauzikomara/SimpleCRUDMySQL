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

public class MainActivity extends AppCompatActivity {

    private EditText editTextNama, editTextAlamat, editTextKesan;
    private Button simpan, kembali;
    private RadioGroup rgJenkel;
    private static final String REGISTER_URL = "http://192.168.20.101/web_service/Kesan/kesan.php";
    String fullname, gender, address, kesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNama = (EditText) findViewById(R.id.etNama);
        rgJenkel = (RadioGroup) findViewById(R.id.radioGroup);
        editTextAlamat = (EditText) findViewById(R.id.etAlamat);
        editTextKesan = (EditText) findViewById(R.id.etDes);
        simpan = (Button) findViewById(R.id.btnSimpan);
        kembali = (Button) findViewById(R.id.btnKembali);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jk = null;
                switch (rgJenkel.getCheckedRadioButtonId()) {
                    case R.id.rbLakilaki:
                        jk = "Laki-Laki";
                        break;
                    case R.id.rbPerempuan:
                        jk = "Perempuan";
                        break;
                }

                fullname = editTextNama.getText().toString();
                gender = jk;
                address = editTextAlamat.getText().toString();
                kesan = editTextKesan.getText().toString();

                pesan(fullname, gender, address, kesan);
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void pesan(String fullname, String gender, String address, String kesan) {
        class PesanTiket extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler requestHandler = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", "Loading...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (editTextNama.getText().toString().trim().length() == 0
                        || editTextAlamat.getText().toString().trim().length() == 0
                        || editTextKesan.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(MainActivity.this, DataDiri.class);
                    finish();
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("fullname",params[0]);
                data.put("gender",params[1]);
                data.put("address",params[2]);
                data.put("kesan",params[3]);

                String result = requestHandler.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        PesanTiket ru = new PesanTiket();
        ru.execute(fullname, gender, address, kesan);
    }
}