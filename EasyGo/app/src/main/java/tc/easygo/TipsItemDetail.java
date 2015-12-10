package tc.easygo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TipsItemDetail extends AppCompatActivity {

    public static String KEY_ITEM = "item";
    private TextView tvTitleTips;
    private TipsModel item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_item_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Tips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTitleTips = (TextView)findViewById(R.id.tv_title_tips);
        item = (TipsModel) getIntent().getSerializableExtra(KEY_ITEM);

        tvTitleTips.setText(Html.fromHtml(item.getText()));
    }

    private void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, item.getText() + " ");
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Jual Mobile Murah");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

}
