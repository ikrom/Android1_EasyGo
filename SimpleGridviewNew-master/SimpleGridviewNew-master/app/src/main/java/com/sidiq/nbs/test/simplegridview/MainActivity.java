package com.sidiq.nbs.test.simplegridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    private String[] images = new String[]{
            "https://s-media-cache-ak0.pinimg.com/736x/77/c4/e4/77c4e4dc8f65e6d9e6419c147c77c3a1.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/99/f4/17/99f417c511dfe517cdfd757cf84a4813.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/5d/87/74/5d87746b88da486db35d998bb1d3787f.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/3f/a7/79/3fa7797d95856823334052446ef42b5d.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/66/97/76/669776804ea51cae9b87e1b49172ab53.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/b7/68/fe/b768fefdc7ddf5d2fb74451e132749a3.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/32/74/e5/3274e582ac15651be40ad8d636bd52df.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/a9/28/40/a9284023087dcbac532b2f6a902629ab.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/0b/d6/18/0bd6181b114a0fe6ead4ec565b294e52.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/60/c3/94/60c394942923e9c3a73f57294da25a0d.jpg"
    };

    private GridView gvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Dinosaurs");

        gvItem = (GridView)findViewById(R.id.gv_item);
        ItemGridAdapter itemGridAdapter = new ItemGridAdapter(MainActivity.this, images);
        gvItem.setAdapter(itemGridAdapter);
        gvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailImageActivity.toDetailImageActivity(MainActivity.this, images[position]);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_staggered) {
            StaggeredGridActivity.toStaggeredGridActivity(MainActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
