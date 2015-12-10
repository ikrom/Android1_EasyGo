package tc.easygo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PlanCreateActivity extends AppCompatActivity  implements OnClickListener  {

    //UI References
    private EditText etDate;
    private EditText toDateEtxt;

    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_create);

        //toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Buat Rencana Perjalanan");

        //TimePicker
        final EditText eReminderTime = (EditText)findViewById(R.id.et_time);
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
                            eReminderTime.setText( "Waktu : 0" + selectedHour + " : " + selectedMinute);
                        }
                        else if(selectedHour >= 10 && selectedMinute <10 ){
                            eReminderTime.setText( "Waktu : " + selectedHour + " : 0" + selectedMinute);
                        }
                        else if (selectedHour <10 && selectedHour <10){
                            eReminderTime.setText( "Waktu : 0" + selectedHour + " : 0" + selectedMinute);
                        }
                        else eReminderTime.setText( "Waktu : " + selectedHour + " : " + selectedMinute);
                    }
                }, hour, minute,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //DatePicker
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();
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
                etDate.setText("Tanggal : "+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onClick(View view) {
        if(view == etDate) {
            fromDatePickerDialog.show();
        }
    }

}
