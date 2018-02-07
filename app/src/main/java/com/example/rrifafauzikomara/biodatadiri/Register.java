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

public class Register extends AppCompatActivity {

    private EditText editTextNama, editTextEmail, editTextUsername, editTextPassword, editTextAlamat, editTextTelp;
    private Button btnDaftar, btnKembali;
    private RadioGroup rgJenkel;
    private static final String REGISTER_URL = "http://192.168.20.101/web_service/User/register.php";
    String fullname, email, username, password, gender, address, city, telp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNama = (EditText) findViewById(R.id.etName);
        editTextEmail = (EditText) findViewById(R.id.etEmail);
        editTextUsername = (EditText) findViewById(R.id.etUsername2);
        editTextPassword = (EditText) findViewById(R.id.etPassword2);
        rgJenkel = (RadioGroup) findViewById(R.id.radioGroup);
        editTextAlamat = (EditText) findViewById(R.id.etAddress);
        editTextTelp = (EditText) findViewById(R.id.etTelp);
        btnDaftar = (Button) findViewById(R.id.btnRegister2);
        btnKembali = (Button) findViewById(R.id.btnCancel);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jk = null;
                switch (rgJenkel.getCheckedRadioButtonId()) {
                    case R.id.rbLakilaki:
                        jk = "laki-laki";
                        break;
                    case R.id.rbPerempuan:
                        jk = "perempuan";
                        break;
                }

                fullname = editTextNama.getText().toString();
                email = editTextEmail.getText().toString();
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();
                gender = jk;
                address = editTextAlamat.getText().toString();
                telp = editTextTelp.getText().toString();

                register(fullname, email, username, password, gender, address, telp);
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void register(String fullname, String email, String username, String password,
                          String gender, String address, String telp) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler requestHandler = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Register.this, "Please Wait", "Loading...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (editTextNama.getText().toString().trim().length() == 0
                        || editTextEmail.getText().toString().trim().length() == 0
                        || editTextUsername.getText().toString().trim().length() == 0
                        || editTextPassword.getText().toString().trim().length() == 0
                        || editTextAlamat.getText().toString().trim().length() == 0
                        || editTextTelp.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(Register.this, Login.class);
                    finish();
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("fullname",params[0]);
                data.put("email",params[1]);
                data.put("username",params[2]);
                data.put("password",params[3]);
                data.put("gender",params[4]);
                data.put("address",params[5]);
                data.put("telp",params[6]);

                String result = requestHandler.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(fullname, email, username, password, gender, address, telp);
    }
}
