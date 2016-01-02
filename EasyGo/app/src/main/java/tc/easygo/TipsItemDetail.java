package tc.easygo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import tc.easygo.adapter.DetailTipsAdapter;
import tc.easygo.connection.PostRequest;
import tc.easygo.models.TipsModel;
import tc.easygo.models.DetailsTipsModel;

public class TipsItemDetail extends AppCompatActivity implements AsyncResponse {

    private ArrayList<DetailsTipsModel> detailTipsItem;

    private ListView lvDetailTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_item_detail);

        /*Toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Tips Populer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String KEY_ITEM="tips";
        TipsModel item;
        item = (TipsModel) getIntent().getSerializableExtra(KEY_ITEM);

        TextView tvTitleTips;
        tvTitleTips = (TextView) findViewById(R.id.tv_title_tips);
        tvTitleTips.setText(Html.fromHtml(item.getDeskripsi()));

        final String id = String.valueOf(item.getId());

        detailTipsItem = new ArrayList<>();


        //post data
        GetDataPost(id);
    }

    private void GetDataPost(String idTips) {
        HashMap<String, String> registerParams = new HashMap<>();
        registerParams.put("id_tips", idTips);

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("gettipstravellingbyid");
    }

    public void processFinish(String output) {
        try {
            JSONObject postParentObject = new JSONObject(output);
            JSONArray Objectdata = postParentObject.getJSONArray("data");

            DetailsTipsModel detailList;
            for(int i=0;i<Objectdata.length();i++){
                detailList = new DetailsTipsModel();
                JSONObject finalObjectData = Objectdata.getJSONObject(i);
                String ObjectTipsList = finalObjectData.getString("deskripsi");
                detailList.setDeskripsi(ObjectTipsList);
                detailTipsItem.add(detailList);
            }
            lvDetailTips = (ListView)findViewById(R.id.lv_detail_tips);
            DetailTipsAdapter detailTipsAdapter = new DetailTipsAdapter(TipsItemDetail.this, detailTipsItem);
            lvDetailTips.setAdapter(detailTipsAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /*private void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, item.getText() + " ");
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Jual Mobile Murah");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }*/

}
