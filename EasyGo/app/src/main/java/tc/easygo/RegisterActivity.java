package tc.easygo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import tc.easygo.connection.PostRequest;

public class RegisterActivity extends AppCompatActivity implements AsyncResponse {

    String getStatus, getMessage, getData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText boxNama = (EditText)findViewById(R.id.boxNama);
        final EditText boxEmail = (EditText)findViewById(R.id.boxEmail);
        final EditText boxSandi = (EditText)findViewById(R.id.boxSandi);
        final Spinner countrySpinner = (Spinner)findViewById(R.id.country_spinner);
        final Spinner genderSpinner = (Spinner)findViewById(R.id.gender_spinner);

        Button btnDaftar = (Button)findViewById(R.id.btn_daftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = boxNama.getText().toString();
                String email = boxEmail.getText().toString();
                String sandi = boxSandi.getText().toString();
                String country = countrySpinner.getSelectedItem().toString();
                String gender = genderSpinner.getSelectedItem().toString();

                GetDataPost(nama, email, sandi, country, gender);
            }
        });

    }

    private void GetDataPost(String nama, String email, String sandi, String country, String gender) {
        HashMap<String, String> registerParams = new HashMap<>();

        /*
        {
            "status":"200",
            "message":"registerSuccess"
        }
        {
            "status":"200",
            "message":"registerFailed",
            "data":"Username sudah terdaftar"
         }
         */

        registerParams.put("name", nama);
        registerParams.put("email", email);
        registerParams.put("username", nama);
        registerParams.put("password", sandi);
        registerParams.put("idcountry", country);
        registerParams.put("idcity", "1");
        registerParams.put("idgender", gender);
        registerParams.put("birthday", "1994-10-10");
        registerParams.put("image", convertImageToString());
        registerParams.put("status", "Selalu bersyukur");
        registerParams.put("dateofregister", "2015-10-10");

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("register");
        return;
    }

    public String convertImageToString() {
        String imageUri = "drawable://" + R.drawable.star_big;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imageUri, options);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }

    @Override
    public void processFinish(String output) {
        try {
            JSONObject postParentObject = new JSONObject(output);
            String Objectdata = postParentObject.getString("data");
            JSONObject postFinalObject = new JSONObject(Objectdata);

            getStatus = (String)postFinalObject.getString("status");
            getMessage = (String)postFinalObject.getString("message");
            getData = (String)postFinalObject.getString("data");

            if (getMessage.equals("registerSuccess")) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                cekLog("Pendaftaran Berhasil");
            } else {
                cekLog(getData);
            }

            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
            cekLog("Pendaftaran Gagal");
            //cekLog(String.valueOf(e));
        }
    }

    public void cekLog(String iniCek){
        Log.d("Kesalahan : ", iniCek);

        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(iniCek)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
