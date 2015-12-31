package com.sidiq.nbs.test.simplegridview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;

public class StaggeredGridActivity extends AppCompatActivity {
    private StaggeredGridView staggeredGridView;
    private String[] items = new String[]{
            "https://s-media-cache-ak0.pinimg.com/736x/31/72/90/3172904c6591b127ac71b66b300a87f4.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/c4/93/d1/c493d196b1b4e0ff61d8cb4a13727916.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/54/a3/23/54a323dcafda85b7fda6883c00f2860a.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/80/d5/b8/80d5b8ccfef474f5c895400acdbea63b.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/0e/d7/7a/0ed77a3208f982a6f552fbf30f7d4a4c.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/7f/2e/79/7f2e790b6ebb34699b887181d7a6a3ed.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/fd/66/f0/fd66f045d95449469fa8b41b4b975edb.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/d7/57/ca/d757ca1df5224dc46633a42e3c34bb90.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/96/2a/d1/962ad10951a221f4ca26bb08e911da10.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/ac/89/c7/ac89c7aaef5754bfd5d7b4a698fdb6cc.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_grid);

        getSupportActionBar().setTitle("Star Wars");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        staggeredGridView = (StaggeredGridView)findViewById(R.id.gv_staggered);
        ItemGridAdapter itemGridAdapter = new ItemGridAdapter(StaggeredGridActivity.this, items);
        staggeredGridView.setAdapter(itemGridAdapter);
        staggeredGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailImageActivity.toDetailImageActivity(StaggeredGridActivity.this, items[position]);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void toStaggeredGridActivity(Activity activity){
        activity.startActivity(new Intent(activity, StaggeredGridActivity.class));
    }
}
