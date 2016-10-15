package com.asbozh.softuni.finalproject.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.database.Record;

import java.io.File;


public class ViewEntryActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Record mRecord;
    TextView tvTitle, tvMoreDetails, tvCategory, tvAmount, tvCurrency, tvDate, tvLocation;
    ImageView ivPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);
        initToolbar();
        mRecord = getIntent().getExtras().getParcelable("ENTRY");
        if (mRecord.getFinancesTypeOfCategory().equalsIgnoreCase("income")) {
            getSupportActionBar().setTitle(getString(R.string.fab_menu_income));
        } else {
            getSupportActionBar().setTitle(getString(R.string.fab_menu_expense));
        }
        setViews();
    }


    private void setImage() {
        if (mRecord.getImage() != null) {
            if (!mRecord.getImage().equalsIgnoreCase("") && new File(mRecord.getImage()).exists()) {
                // Get the dimensions of the View
                int targetW = ivPic.getWidth();
                int targetH = ivPic.getHeight();

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mRecord.getImage(), bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;

                Bitmap bitmap = BitmapFactory.decodeFile(mRecord.getImage(), bmOptions);
                ivPic.setImageBitmap(bitmap);
                ivPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent showPicIntent = new Intent(ViewEntryActivity.this, ViewPic.class);
                        showPicIntent.putExtra("PIC_PATH", mRecord.getImage());
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(ViewEntryActivity.this, ivPic, "picture");
                        startActivity(showPicIntent, options.toBundle());

                    }
                });
            }
        }
    }

    private void setViews() {
        tvTitle = (TextView) findViewById(R.id.tvViewEntryTitle);
        tvTitle.setText(mRecord.getTitle());
        tvMoreDetails = (TextView) findViewById(R.id.tvViewEntryMoreDetails);
        tvMoreDetails.setText(mRecord.getMoreDetails());
        tvCategory = (TextView) findViewById(R.id.tvViewEntryCategory);
        tvCategory.setText(mRecord.getCategory());
        tvAmount = (TextView) findViewById(R.id.tvViewEntryAmount);
        tvAmount.setText(mRecord.getAmount());
        tvDate = (TextView) findViewById(R.id.tvViewEntryDate);
        tvDate.setText(mRecord.getDate());
        tvLocation = (TextView) findViewById(R.id.tvViewEntryLocation);
        tvLocation.setText(mRecord.getLocation());

        ivPic = (ImageView) findViewById(R.id.ivViewEntryPic);
        ivPic.post(new Runnable() {
            @Override
            public void run() {
                setImage();
            }
        });
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
