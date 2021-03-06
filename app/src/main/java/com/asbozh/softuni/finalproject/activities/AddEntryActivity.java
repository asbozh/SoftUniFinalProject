package com.asbozh.softuni.finalproject.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.database.Category;
import com.asbozh.softuni.finalproject.database.Finances;
import com.asbozh.softuni.finalproject.database.Record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddEntryActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_CHOOSE = 2;
    static final int REQUEST_PERMISSION_STORAGE = 3;
    static final int REQUEST_PERMISSION_LOCATION = 4;

    String mCurrentPhotoPath = null;

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;

    SharedPreferences sharedPreferences;

    LocationManager mLocationManager;

    Toolbar mToolbar;
    String entryType = "unknown";
    TextView tvDateEntry;
    EditText etAmount, etTitle, etMoreDetails, etLocation;
    TextInputLayout tilAmount, tilTitle;
    Spinner spinnerCat;
    ImageButton ibCalendarPicker;
    ImageView ivEntryPic;
    Button btnAdd, btnCancel, btnCapture, btnChoose;
    CheckBox cbLocation;
    Bitmap bitmap;


    private List<Finances> mFinancesList;
    private List<Category> mCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        initToolbar();
        entryType = getIntent().getStringExtra("TYPE_KEY");
        setViews();
        setCatSpinner();
        if(savedInstanceState != null) {
            Bitmap bitmap = savedInstanceState.getParcelable("image");
            ivEntryPic.setImageBitmap(bitmap);
        }

    }

    private void setCatSpinner() {
        ArrayList<String> categoryArray = new ArrayList<>();
        if (entryType.equalsIgnoreCase("INCOME")) {
            mFinancesList = Finances.listAll(Finances.class);
            mCategoryList = mFinancesList.get(0).getAllCategories(); // get only income categories
        } else if (entryType.equalsIgnoreCase("EXPENSE")) {
            mFinancesList = Finances.listAll(Finances.class);
            mCategoryList = mFinancesList.get(1).getAllCategories(); // get only expense categories
        }
        for (int i = 0; i < mCategoryList.size(); i++) {
            categoryArray.add(mCategoryList.get(i).getCategoryName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categoryArray);
        spinnerCat.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateEntry();
            }

        };
        updateDateEntry();
        if (entryType.equalsIgnoreCase("INCOME")) {
            getSupportActionBar().setTitle(R.string.add_income_entry);
            updateTitle(getString(R.string.from));
        } else if (entryType.equalsIgnoreCase("EXPENSE")) {
            getSupportActionBar().setTitle(R.string.add_expense_entry);
            updateTitle(getString(R.string.for_expense));
        }

    }

    private void updateTitle(String string) {
        etTitle.setText(entryType + " " + string + " " + spinnerCat.getSelectedItem().toString() + " " + getString(R.string.on) + " " + tvDateEntry.getText());
    }

    private void updateDateEntry() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        tvDateEntry.setText(sdf.format(myCalendar.getTime()));
    }

    private void setViews() {
        tvDateEntry = (TextView) findViewById(R.id.tvDateEntry);
        etLocation = (EditText) findViewById(R.id.etLocation);
        ibCalendarPicker = (ImageButton) findViewById(R.id.ibCalendarPicker);
        ibCalendarPicker.setOnClickListener(this);
        ivEntryPic = (ImageView) findViewById(R.id.ivEntryPic);
        btnAdd = (Button) findViewById(R.id.bAddEntry);
        btnAdd.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.bCancelEntry);
        btnCancel.setOnClickListener(this);
        btnCapture = (Button) findViewById(R.id.bCapturePic);
        btnCapture.setOnClickListener(this);
        btnChoose = (Button) findViewById(R.id.bChoosePic);
        btnChoose.setOnClickListener(this);
        cbLocation = (CheckBox) findViewById(R.id.cbLocation);
        cbLocation.setOnClickListener(this);
        etTitle = (EditText) findViewById(R.id.etEntryTitle);
        etAmount = (EditText) findViewById(R.id.etAmount);
        etMoreDetails = (EditText) findViewById(R.id.etEntryDetails);
        spinnerCat = (Spinner) findViewById(R.id.spinnerCat);
        tilAmount = (TextInputLayout) findViewById(R.id.input_amount);
        tilTitle = (TextInputLayout) findViewById(R.id.input_title);
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
                deleteTempPic();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibCalendarPicker:
                new DatePickerDialog(AddEntryActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.bAddEntry:
                if (isUserInputValid()) {
                    saveRecord();
                    Toast.makeText(this, getText(R.string.entry_added), Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case R.id.bCancelEntry:
                deleteTempPic();
                finish();
                break;
            case R.id.bCapturePic:
                if (mCurrentPhotoPath == null) {
                    dispatchTakePictureIntent();
                } else {
                    deleteTempPic();
                    dispatchTakePictureIntent();
                }
                break;
            case R.id.bChoosePic:
                if (mCurrentPhotoPath == null) {
                    if (isStoragePermissionGranted()) {
                        dispatchChoosePictureIntent();
                    }
                } else {
                    deleteTempPic();
                    if (isStoragePermissionGranted()) {
                        dispatchChoosePictureIntent();
                    }
                }
                break;
            case R.id.cbLocation:
                if (cbLocation.isChecked()) {
                    if (isLocationPermissionGranted()) {
                        addUserLocation();
                    }
                } else {
                    etLocation.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    private void saveRecord() {
        Category category = null;
        for (int i = 0; i < mCategoryList.size(); i++) {
            if (mCategoryList.get(i).getCategoryName().equalsIgnoreCase(spinnerCat.getSelectedItem().toString())) {
                category = mCategoryList.get(i);
            }
        }

        // Add Amount to the Category
        double categoryPrice = Double.valueOf(category.getCategoryTotalPrice());
        categoryPrice += Double.valueOf(etAmount.getText().toString());
        category.setCategoryTotalPrice(String.valueOf(categoryPrice));
        category.save();

        Record newRecord = new Record(category, etTitle.getText().toString(), etMoreDetails.getText().toString(), etAmount.getText().toString(), tvDateEntry.getText().toString(),
                etLocation.getText().toString(), mCurrentPhotoPath);
        newRecord.save();

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        String lastEntryDate = sdf.format(myCalendar.getTime());
        sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_entry", lastEntryDate);
        editor.apply();
    }

    private boolean isUserInputValid() {
        if (etAmount.getText().toString().trim().isEmpty() || etAmount.getText().toString().trim().equals(".") || etAmount.getText().toString().trim().startsWith(".")) {
            tilAmount.setErrorEnabled(true);
            tilAmount.setError(getString(R.string.err_msg_amount));
            return false;
        } else {
            tilAmount.setErrorEnabled(false);
        }
        if (etTitle.getText().toString().trim().isEmpty()) {
            tilTitle.setErrorEnabled(true);
            tilTitle.setError(getString(R.string.err_msg_title));
            return false;
        } else {
            tilTitle.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isLocationPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
                return false;
            }
        } else {
            return true;
        }
    }

    private void addUserLocation() {
        etLocation.setVisibility(View.VISIBLE);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) { // check if the last known location isn't older than 2 minutes
            addAddress(location.getLatitude(), location.getLongitude());
        } else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            addAddress(location.getLatitude(), location.getLongitude());
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.removeUpdates(this);
        }
    }

    private void addAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            etLocation.setText(addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName());
        } else {
            etLocation.setText(getText(R.string.location_unknown));
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    private void deleteTempPic() {
        if (mCurrentPhotoPath != null) {
            File tempFile = new File(mCurrentPhotoPath);
            tempFile.delete();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.asbozh.softuni.finalproject",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        }
        if (requestCode == REQUEST_IMAGE_CHOOSE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String mChosenFilePath = cursor.getString(columnIndex);
            cursor.close();
            saveChosenPicLocally(mChosenFilePath);
            ivEntryPic.setImageBitmap(BitmapFactory
                    .decodeFile(mCurrentPhotoPath));

        }
    }

    private void saveChosenPicLocally(String chosenPicDestination) {
        File photoFile = null;
        InputStream in = null;
        OutputStream out = null;
        File chosenPic = new File(chosenPicDestination);
        try {
            photoFile = createImageFile();
            in = new FileInputStream(chosenPic);
            out = new FileOutputStream(photoFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        //saving the full path to save in DB
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivEntryPic.getWidth();
        int targetH = ivEntryPic.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ivEntryPic.setImageBitmap(bitmap);
    }

    private void dispatchChoosePictureIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_CHOOSE);
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
                return false;
            }
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            dispatchChoosePictureIntent();
        }
        if (requestCode == REQUEST_PERMISSION_LOCATION && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            addUserLocation();
        } else if (requestCode == REQUEST_PERMISSION_LOCATION && grantResults[0]== PackageManager.PERMISSION_DENIED) {
            cbLocation.setChecked(false);
            etLocation.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("image", bitmap);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteTempPic();
    }
}
