package tc.easygo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TipsActivity extends AppCompatActivity {
    String[][] data = new String[][]{
            {"50 Barang yang harus dibawa saat traveling"},
            {"Tips Melipat dan mengorganisir pakaian serta barang lain"},
            {"Tips mengatasi Jetlag"},
            {"Panduan sebelum melakukan Hiking"},
            {"Tips agar tidak gosong saat berlibur ke pantai"},
            {"Tips mengepak pakaian yang sederhana"},
            {"Daftar barang bawaan untuk anak-anak dan balita"},
            {"Daftar barang bawaan untuk anak-anak dan balita"}
    };

    private ListView lvTipsItem;
    private ArrayList<TipsModel> tipsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tips Travelling");

        lvTipsItem = (ListView)findViewById(R.id.lv_tips_item);
        tipsItem = new ArrayList<>();

        TipsModel tips = null;

        for (int i = 0; i < data.length; i++){
            tips = new TipsModel();
            tips.setText(data[i][0]);
            tipsItem.add(tips);
        }

        TipsAdapter adapter = new TipsAdapter(TipsActivity.this, tipsItem);
        lvTipsItem.setAdapter(adapter);

        lvTipsItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TipsModel tps = tipsItem.get(position);

                Intent intent = new Intent(TipsActivity.this, TipsItemDetail.class);
                intent.putExtra(TipsItemDetail.KEY_ITEM,tps);
                startActivityForResult(intent, 0);
            }
        });

    }
}
