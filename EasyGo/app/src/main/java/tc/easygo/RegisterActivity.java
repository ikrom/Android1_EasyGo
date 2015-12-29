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
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tc.easygo.connection.PostRequest;

public class RegisterActivity extends AppCompatActivity implements AsyncResponse {

    private EditText boxNama, boxEmail, boxSandi;
    private Spinner countrySpinner, genderSpinner;
    private String getStatus, getMessage, getData;
    private Button btnDaftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        boxNama = (EditText)findViewById(R.id.boxNama);
        boxEmail = (EditText)findViewById(R.id.boxEmail);
        boxSandi = (EditText)findViewById(R.id.boxSandi);
        countrySpinner = (Spinner)findViewById(R.id.country_spinner);
        genderSpinner = (Spinner)findViewById(R.id.gender_spinner);
        btnDaftar = (Button)findViewById(R.id.btn_daftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = boxNama.getText().toString();
                String email = boxEmail.getText().toString();
                String sandi = boxSandi.getText().toString();
                String country = String.valueOf(countrySpinner.getSelectedItemId());
                String gender = String.valueOf(genderSpinner.getSelectedItemId());

                //cekLog("nama : " +nama+ " | email : " +email+ " | sandi : " +sandi+ " | country : " +country+ " | gender : " +gender);

                GetDataPost(nama, email, sandi, country, gender);
            }
        });
    }

    private void GetDataPost(String nama, String email, String sandi, String country, String gender) {
        HashMap<String, String> registerParams = new HashMap<>();

        registerParams.put("name", nama);
        registerParams.put("email", email);
        registerParams.put("username", nama);
        registerParams.put("password", sandi);
        registerParams.put("idcountry", country);
        registerParams.put("idcity", "1");
        registerParams.put("idgender", gender);
        registerParams.put("birthday", "1994-10-10");
        registerParams.put("image", convertImageToString());
        registerParams.put("status", "Mantap");
        registerParams.put("dateofregister", "2015-10-10");


        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("register"); //name, email, username, password, idcountry, idcity, idgender, birthday, image, status, dateofregister

        return;
    }

    public String convertImageToString() {
        String imageBase64 = "http:\\/\\/www.fightersgeneration.com\\/characters\\/cloud-advent-bust.jpg";
        return imageBase64;
    }

    @Override
    public void processFinish(String output) {
        try {
            JSONObject postParentObject = new JSONObject(output);
            String Objectdata = postParentObject.getString("message");
            cekLog(Objectdata);
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
            cekLog(String.valueOf(e));
        }
    }

    public void cekLog(String iniCek){
        Log.d("", iniCek);

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
