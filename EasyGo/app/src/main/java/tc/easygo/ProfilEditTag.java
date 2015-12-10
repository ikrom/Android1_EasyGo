package tc.easygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableRow;

public class ProfilEditTag extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_edit_tag);

        int Array_Count=0;
        final String Str_Array[] = {
                "Turis Ransel",
                "Penggemar sejarah",
                "Penggemar budaya",
                "Pecinta pantai",
                "Penggemar olahraga esktrim",
                "Pencari kedamaian",
                "Penjelajah urban",
                "Pecinta alam",
                "Wisatawan hemat",
                "Pecinta seni dan arsitektur",
                "Penggemar belanja",
                "Pecinta kuliner",
                "Vegetarian"
        };

        Array_Count=Str_Array.length;

        final LinearLayout my_layout = (LinearLayout)findViewById(R.id.row_checkbox);

        for (int i = 0; i < Array_Count; i++)
        {
            final TableRow row =new TableRow(this);
            row.setId(i);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            final CheckBox checkBox = new CheckBox(this);
            final int finalI = i;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
            checkBox.setId(finalI);
            checkBox.setText(Str_Array[finalI]);
            row.addView(checkBox);
            my_layout.addView(row);
        }
    }
}
