package com.wizeline.omarsanchez.crashcourse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.wizeline.omarsanchez.crashcourse.adapters.AdapterExample;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView header;
    int imageSelected = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChanger(getNextPhoto());
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterExample());
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctlLayout);
        header = (ImageView) findViewById(R.id.header_image);
        colorChanger(getNextPhoto());

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void colorChanger(int image) {
        header.setImageResource(image);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getVibrantColor(ContextCompat.getColor(MainActivity.this, R.color.primary)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Palette.Swatch swatch = palette.getVibrantSwatch();
                    if (swatch != null) {
                        float[] hsl = swatch.getHsl();
                        hsl[2] += .3f;
                        getWindow().setStatusBarColor(Color.HSVToColor(hsl));
                    } else {
                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.primary_dark));
                    }
                }
            }
        });

    }

    private int getNextPhoto() {
        if (imageSelected == 0) {
            imageSelected = 1;
            return R.drawable.wizeline;
        } else {
            imageSelected = 0;
            return R.drawable.image2;
        }
    }
}
