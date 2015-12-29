package tc.easygo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import tc.easygo.connection.PostRequest;

public class ProfilOtherActivity extends AppCompatActivity implements AsyncResponse{

    private TextView tvNama, tvUlasan, tvTeman, tvProgres, tvTanggalDaftar, tvStatus, tvGenderUmur, tvKotaNegara, tvSudahKemana, tvUlasanBermanfaat, tvUlasanWisata;
    private Button gaya1, gaya2, gaya3;
    private ImageView profilePicture;
    private String idUser, idTargetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_other);

        tvNama = (TextView)findViewById(R.id.tvNama);
        tvSudahKemana = (TextView)findViewById(R.id.tvSudahKemana);
        tvProgres = (TextView)findViewById(R.id.tvProgres);
        tvUlasan = (TextView)findViewById(R.id.tvUlasan);
        tvTeman = (TextView)findViewById(R.id.tvTeman);
        tvKotaNegara = (TextView)findViewById(R.id.tvKotaNegara);
        tvGenderUmur = (TextView)findViewById(R.id.tvGenderUmur);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvUlasanBermanfaat = (TextView)findViewById(R.id.tvUlasanBermanfaat);
        tvUlasanWisata = (TextView)findViewById(R.id.tvUlasanObjek);
        tvTanggalDaftar = (TextView)findViewById(R.id.tvTanggalDaftar);
        gaya1 = (Button)findViewById(R.id.gaya1);
        gaya2 = (Button)findViewById(R.id.gaya2);
        gaya3 = (Button)findViewById(R.id.gaya3);
        profilePicture = (ImageView)findViewById(R.id.profilePicture);

        idUser = "2";
        idTargetUser = "1";

        GetDataPostEvent(idUser, idTargetUser);
    }

    private void GetDataPostEvent(String idUser, String idTargetUser) {
        HashMap<String, String> registerParams = new HashMap<>();
        registerParams.put("id_user", idUser);
        registerParams.put("id_target_user", idTargetUser);

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("getprofilebyid"); //id_user, id_target_user
        return;
    }

    @Override
    public void processFinish(String output) {
        try {
            //data
            JSONObject postParentObject = new JSONObject(output);
            String Objectdata = postParentObject.getString("data");
            JSONObject getObjectdata = new JSONObject(Objectdata);
            String DataNama = getObjectdata.getString("nama");
            String DataGambar = getObjectdata.getString("gambar");
            String DataStatus = getObjectdata.getString("status");
            String DataTanggalLahir = getObjectdata.getString("tanggal_lahir");
            String DataTanggalDaftar = getObjectdata.getString("tanggal_daftar");
            String DataJenisKelamin = getObjectdata.getString("jenis_kelamin");
            String DataKota = getObjectdata.getString("kota");
            String DataNegara = getObjectdata.getString("negara");
            String DataJumlahReview = getObjectdata.getString("jumlah_review");
            String DataProgres = getObjectdata.getString("progres");
            String DataJumlahTeman = getObjectdata.getString("jumlah_teman");

            //sudah_kemana
            JSONObject postParentObject2 = new JSONObject(Objectdata);
            String Objectdata2 = postParentObject2.getString("sudah_kemana");
            JSONArray Objectdata2Array = new JSONArray(Objectdata2);

            //lencana
            JSONObject postParentObject3 = new JSONObject(Objectdata);
            String Objectdata3 = postParentObject3.getString("lencana");
            JSONObject getObjectData3 = new JSONObject(Objectdata3);
            String ulasanWisata = getObjectData3.getString("ulasan_wisata");
            String ulasanBermanfaat = getObjectData3.getString("ulasan_bermanfaat");

            //gaya_wisata
            //JSONObject postParentObject4 = new JSONObject(Objectdata);
            //String Objectdata4 = postParentObject4.getString("gaya_wisata");

            //pengisian xml
            tvNama.setText(DataNama);
            tvUlasan.setText(String.valueOf(Integer.parseInt(ulasanBermanfaat) + Integer.parseInt(ulasanWisata)) + " Ulasan  ");
            tvTeman.setText("  " + DataJumlahTeman + " Teman");
            tvProgres.setText(String.valueOf((int)Math.round(Float.parseFloat(DataProgres) * 100.0)) + "%");
            tvTanggalDaftar.setText("Aktif sejak, " + DataTanggalDaftar);
            tvStatus.setText(DataStatus);
            tvGenderUmur.setText(DataJenisKelamin + ", " + DataTanggalLahir);
            tvKotaNegara.setText(DataKota + ", " + DataNegara);
            tvSudahKemana.setText(Objectdata2Array.length() + " Destinasi Wisata");
            tvUlasanBermanfaat.setText(ulasanBermanfaat + " Ulasan Bermanfaat");
            tvUlasanWisata.setText(ulasanWisata + " Ulasan Objek Wisata");
            //profilePicture.setImageBitmap(getBitmapFromURL(DataGambar));
            gaya1.setText("");
            gaya2.setText("");
            gaya3.setText("");

        } catch (JSONException e) {
            e.printStackTrace();
            cekLog(String.valueOf(e));
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
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
