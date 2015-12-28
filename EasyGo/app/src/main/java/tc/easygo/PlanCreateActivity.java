package tc.easygo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import tc.easygo.connection.PostRequest;

public class PlanCreateActivity extends AppCompatActivity  implements OnClickListener, AsyncResponse  {

    //UI References
    private EditText etDate;
    private EditText toDateEtxt;
    private CheckBox ingatkanSaya;

    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_create);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Atribut Layout
        Button btnSimpan = (Button)findViewById(R.id.btn_simpan);
        ingatkanSaya = (CheckBox)findViewById(R.id.ingatkan_saya);
        final Spinner tujuanWisata = (Spinner)findViewById(R.id.tujuan_wisata);
        final Spinner event = (Spinner)findViewById(R.id.event);
        final Spinner jenisRencana = (Spinner)findViewById(R.id.jenis_rencana);
        final EditText catatan = (EditText)findViewById(R.id.catatan);

        //Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Buat Rencana Perjalanan");

        //TimePicker
        final EditText eReminderTime = (EditText)findViewById(R.id.et_time);


        jenisRencana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(jenisRencana.getSelectedItem().equals("Event")) {
                    event.setVisibility(View.VISIBLE);
                    tujuanWisata.setVisibility(View.GONE);
                }
                else {
                    event.setVisibility(View.GONE);
                    tujuanWisata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        eReminderTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(PlanCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour < 10 && selectedMinute >=10 ){
                            eReminderTime.setText("0" + selectedHour + ":" + selectedMinute + ":00");
                        }
                        else if(selectedHour >= 10 && selectedMinute <10 ){
                            eReminderTime.setText(selectedHour + ":0" + selectedMinute + ":00");
                        }
                        else if (selectedHour <10 && selectedHour <10){
                            eReminderTime.setText("0" + selectedHour + ":0" + selectedMinute + ":00");
                        }
                        else eReminderTime.setText(selectedHour + ":" + selectedMinute + ":00");
                    }
                }, hour, minute,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //DatePicker
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();

        btnSimpan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCatatan = catatan.getText().toString();
                String strTujuanWisata = Long.toString(tujuanWisata.getSelectedItemId());
                String strEvent = Long.toString(event.getSelectedItemId());
                String strIngatkan = getValueIngatkanSaya();
                String strWaktu = eReminderTime.getText().toString();
                String strTanggal = etDate.getText().toString();
                String user = CheckPreference();

                //cekLog("Tempat Wisata : " + user + "," + strTujuanWisata + "," + strCatatan + "," + strTanggal + "," + strWaktu + "," + strIngatkan);

                if(jenisRencana.getSelectedItem().equals("Event"))
                    GetDataPostEvent(user, strEvent, strCatatan, strTanggal, strWaktu, strIngatkan);
                else
                    GetDataPostWisata(user, strTujuanWisata, strCatatan, strTanggal, strWaktu, strIngatkan);

            }
        });

    }

    private String getValueIngatkanSaya() {
        if (ingatkanSaya.isChecked())
            return "1";
        else
            return "0";
    }

    private String CheckPreference() {
        String cekIdUser = sharedPreferences.getString("id", null);
        return cekIdUser;
    }

    private void findViewsById() {
        etDate = (EditText) findViewById(R.id.et_date);
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();

        toDateEtxt = (EditText) findViewById(R.id.et_date);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        etDate.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onClick(View view) {
        if(view == etDate) {
            fromDatePickerDialog.show();
        }
    }

    private void GetDataPostWisata(String user, String wisata, String catatan, String tanggal, String waktu, String ingatkan) {
        HashMap<String, String> registerParams = new HashMap<>();
        registerParams.put("id_user", user);
        registerParams.put("id_wisata", wisata);
        registerParams.put("catatan", catatan);
        registerParams.put("tanggal", tanggal);
        registerParams.put("waktu", waktu);
        registerParams.put("ingatkan", ingatkan);

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("addrencanaperjalanantempatwisata"); //id_user, id_wisata, catatan, tanggal, waktu, ingatkan (1/0)
        return;
    }

    private void GetDataPostEvent(String user, String event, String catatan, String tanggal, String waktu, String ingatkan) {
        HashMap<String, String> registerParams = new HashMap<>();
        registerParams.put("id_user", user);
        registerParams.put("id_wisata", event);
        registerParams.put("catatan", catatan);
        registerParams.put("tanggal", tanggal);
        registerParams.put("waktu", waktu);
        registerParams.put("ingatkan", ingatkan);

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("addrencanaperjalananevent"); //id_user, id_event, catatan, tanggal, waktu, ingatkan (1/0)
        return;
    }


    @Override
    public void processFinish(String output) {
        try {
            JSONObject postParentObject = new JSONObject(output);
            String Objectdata = postParentObject.getString("message");
            //JSONObject postFinalObject = new JSONObject(Objectdata);
            cekLog(Objectdata);

            Intent intent = new Intent(PlanCreateActivity.this,PlanActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
            cekLog(String.valueOf(e));
            //cekLog("Isi semua pilihan.");
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
