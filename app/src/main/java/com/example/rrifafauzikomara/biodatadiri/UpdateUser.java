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

public class UpdateUser extends AppCompatActivity {

    private EditText editTextId, editTextNama, editTextEmail, editTextUsername, editTextPassword, editTextAlamat, editTextTelp;
    private Button btnUpdate, btnHapus;
    private RadioGroup rgJenkel;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        Intent intent = getIntent();
        id = intent.getStringExtra("user_id");

        editTextId = (EditText) findViewById(R.id.etIdU);
        editTextNama = (EditText) findViewById(R.id.etNameU2);
        editTextEmail = (EditText) findViewById(R.id.etEmailU2);
        editTextUsername = (EditText) findViewById(R.id.etUsernameU2);
        editTextPassword = (EditText) findViewById(R.id.etPasswordU2);
        editTextAlamat = (EditText) findViewById(R.id.etAddressU2);
        rgJenkel = (RadioGroup) findViewById(R.id.radioGroup3);
        editTextTelp = (EditText) findViewById(R.id.etTelpU2);
        btnUpdate = (Button) findViewById(R.id.btnUpdateUser);
        btnHapus = (Button) findViewById(R.id.btnDeleteUser);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNama.getText().toString().trim().length() == 0
                        || editTextEmail.getText().toString().trim().length() == 0
                        || editTextUsername.getText().toString().trim().length() == 0
                        || editTextPassword.getText().toString().trim().length() == 0
                        || editTextAlamat.getText().toString().trim().length() == 0
                        || editTextTelp.getText().toString().trim().length() == 0) {
                    Toast.makeText(UpdateUser.this, "Mohon datanya dilengkapi terlebih dahulu!", Toast.LENGTH_LONG).show();
                } else {
                    updateUser();
                    startActivity(new Intent(UpdateUser.this, DataUser.class));
                    finish();
                }
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteUser();
            }
        });

        editTextId.setText(id);
        getUser();
    }

    private void getUser(){
        class GetUser extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateUser.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showUser(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://192.168.20.101/web_service/User/get_user.php?id=",id);
                return s;
            }
        }
        GetUser gu = new GetUser();
        gu.execute();
    }

    private void showUser(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            String fullname = c.getString("fullname");
            String email = c.getString("email");
            String username = c.getString("username");
            String password = c.getString("password");
            String address = c.getString("address");
            String telp = c.getString("telp");

            editTextNama.setText(fullname);
            editTextEmail.setText(email);
            editTextUsername.setText(username);
            editTextPassword.setText(password);
            editTextAlamat.setText(address);
            editTextTelp.setText(telp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateUser(){
        String jk = null;
        switch (rgJenkel.getCheckedRadioButtonId()) {
            case R.id.radioButton3:
                jk = "laki-laki";
                break;
            case R.id.radioButton4:
                jk = "perempuan";
                break;
        }

        final String fullname = editTextNama.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String gender = jk;
        final String address = editTextAlamat.getText().toString();
        final String telp = editTextTelp.getText().toString();

        class UpdateUsers extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateUser.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateUser.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("id",id);
                hashMap.put("fullname",fullname);
                hashMap.put("email",email);
                hashMap.put("username",username);
                hashMap.put("password",password);
                hashMap.put("gender",gender);
                hashMap.put("address",address);
                hashMap.put("telp",telp);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest("http://192.168.20.101/web_service/User/update_user.php", hashMap);

                return s;
            }
        }

        UpdateUsers uu = new UpdateUsers();
        uu.execute();
    }

    private void deleteUser(){
        class DeleteUser extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateUser.this, "Deleting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateUser.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://192.168.20.101/web_service/User/delete_user.php?id=", id);
                return s;
            }
        }

        DeleteUser du = new DeleteUser();
        du.execute();
    }

    private void confirmDeleteUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah anda ingin mengapus data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteUser();
                        startActivity(new Intent(UpdateUser.this, DataUser.class));
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