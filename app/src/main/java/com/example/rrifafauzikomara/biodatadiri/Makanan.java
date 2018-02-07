package com.example.rrifafauzikomara.biodatadiri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Makanan extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAsal;
    private EditText editTextHarga;

    private Button buttonAdd;
    private Button buttonView;
    private Button buttonBack;

    private static final String REGISTER_URL = "http://192.168.20.101/web_service/Makanan/makanan.php";
    String nama_makanan, asal_makanan, harga_makanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAsal = (EditText) findViewById(R.id.editTextAsal);
        editTextHarga = (EditText) findViewById(R.id.editTextHarga);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_makanan = editTextName.getText().toString();
                asal_makanan = editTextAsal.getText().toString();
                harga_makanan = editTextHarga.getText().toString();

                makanan(nama_makanan, asal_makanan, harga_makanan);
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        buttonView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Makanan.this, DataMakanan.class));
            }
        });

    }
    private void makanan(String nama_makanan, String asal_makanan, String harga_makanan) {
        class AddMakanan extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler requestHandler = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Makanan.this, "Please Wait", "Loading...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (editTextName.getText().toString().trim().length() == 0
                        || editTextAsal.getText().toString().trim().length() == 0
                        || editTextHarga.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(Makanan.this, DataMakanan.class);
                    finish();
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("nama_makanan",params[0]);
                data.put("asal_makanan",params[1]);
                data.put("harga_makanan",params[2]);

                String result = requestHandler.sendPostRequest(REGISTER_URL,data);

                return result;
            }
        }
        AddMakanan ru = new AddMakanan();
        ru.execute(nama_makanan, asal_makanan, harga_makanan);
    }
}
