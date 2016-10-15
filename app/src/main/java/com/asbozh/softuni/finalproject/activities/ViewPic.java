package com.asbozh.softuni.finalproject.activities;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.asbozh.softuni.finalproject.R;

public class ViewPic extends AppCompatActivity {

    ImageView imageView;
    Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pic);
        imageView = (ImageView) findViewById(R.id.ivViewEntryPic);
        String imagePath = getIntent().getStringExtra("PIC_PATH");
        mBitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(mBitmap);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        supportFinishAfterTransition();
    }
}
