package tc.easygo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import tc.easygo.connection.PostRequest;


public class CalenderActivity extends AppCompatActivity implements AsyncResponse
{

    private TextView tvTanggalAcara, tvBulanAcara, tvTahunAcara, tvJudulAcara, tvLokasiAcara, tvDeskripsiAcara;
    private RelativeLayout keterangan;
    private ImageView ivAcara;
    private String tanggalTmp;
    private String tmpLokasi, tmpJudul, tmpDeskripsi, tmpGambar, tmpTanggal;
    private String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        keterangan = (RelativeLayout)findViewById(R.id.keteranganLayout);
        tvTanggalAcara = (TextView)findViewById(R.id.tvTanggalAcara);
        tvBulanAcara = (TextView)findViewById(R.id.tvBulanAcara);
        tvTahunAcara = (TextView)findViewById(R.id.tvTahunAcara);
        tvJudulAcara = (TextView)findViewById(R.id.calender_NamaEvent);
        tvLokasiAcara = (TextView)findViewById(R.id.calender_LokasiEvent);
        tvDeskripsiAcara = (TextView)findViewById(R.id.calender_KotaEvent);
        ivAcara = (ImageView)findViewById(R.id.ivAcara);
        GetDataPostEvent();

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarView cv = ((CalendarView)findViewById(R.id.calendar_view));
        cv.updateCalendar(events);

        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                //DateFormat df = SimpleDateFormat.getDateInstance();
                //Toast.makeText(CalenderActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
                //cekLog("df.format(date) : " + df.format(date));
                if (tmpTanggal.equals(df.format(date))) {
                        keterangan.setVisibility(View.VISIBLE);
                        ivAcara.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void GetDataPostEvent() {
        HashMap<String, String> registerParams = new HashMap<>();

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("getevent");
        return;
    }

    @Override
    public void processFinish(String output) {
        try {
            //data
            JSONObject postParentObject = new JSONObject(output);
            JSONArray parentArrayAcara = postParentObject.getJSONArray("data");
            for(int i=0;i<parentArrayAcara.length();i++){
                JSONObject objectAcara = parentArrayAcara.getJSONObject(i);
                tmpLokasi = objectAcara.getString("nama_wisata");
                tmpJudul = objectAcara.getString("judul");
                tmpDeskripsi = objectAcara.getString("deskripsi");
                tmpGambar = objectAcara.getString("gambar");
                tmpTanggal = objectAcara.getString("tanggal");
            }

            //pengisian xml
            tvLokasiAcara.setText(tmpLokasi);
            tvJudulAcara.setText(tmpJudul);
            tvDeskripsiAcara.setText(tmpDeskripsi);

            //menampilkan tanggal
            String[] temp;
            String delimiter = "-";
            temp = tmpTanggal.split(delimiter);
            for(int i = 0; i < temp.length ; i++)
            {
                //System.out.println(temp[i]);
                if (i == 0) tvTahunAcara.setText(temp[0]);
                else if (i == 1)tvBulanAcara.setText(bulan[Integer.parseInt(temp[1]) - 1]);
                else if (i == 2) tvTanggalAcara.setText(temp[2]);
            }

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