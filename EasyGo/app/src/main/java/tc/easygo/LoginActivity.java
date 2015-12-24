package tc.easygo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import tc.easygo.connection.PostRequest;

public class LoginActivity extends AppCompatActivity implements AsyncResponse{

    EditText user, pass;
    SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Username = "Username";
    public static final String Password = "Password";
    public static final String Email = "Email";
    public static final String Nama = "Nama";
    public static final String Status = "Status";

    private String getUsername, getNama, getEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin=(Button)findViewById(R.id.btn_login);
        TextView btnProfil=(TextView)findViewById(R.id.tv_profil);
        TextView btnSignin=(TextView)findViewById(R.id.btn_signin);

        user = (EditText)findViewById(R.id.usernameBox);
        pass = (EditText)findViewById(R.id.passwordBox);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user1 = user.getText().toString();
                String pass1 = pass.getText().toString();

                GetDataPost(user1, pass1);
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void GetDataPost(String user1, String pass1) {
        HashMap<String, String> registerParams = new HashMap<>();
        registerParams.put("username", user1);
        registerParams.put("password", pass1);

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("login");
        return;
    }

    @Override
    public void processFinish(String output) {
        try {
            JSONObject postParentObject = new JSONObject(output);
            String Objectdata = postParentObject.getString("data");
            JSONObject postFinalObject = new JSONObject(Objectdata);

            //variabel login
            getUsername = (String)postFinalObject.getString("username");
            getNama = (String)postFinalObject.getString("nama");
            getEmail = (String)postFinalObject.getString("email");

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Username, getUsername);
            editor.putString(Password, pass.getText().toString());
            editor.putString(Nama, getNama);
            editor.putString(Email, getEmail);
            editor.putBoolean(Status, true);
            editor.commit();

            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
            cekLog("Username atau Password salah.");
            //cekLog(String.valueOf(e));
        }
    }

    public void cekLog(String iniCek){
        Log.d("Kesalahan", iniCek);

        new AlertDialog.Builder(this)
                .setTitle("Kesalahan")
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
