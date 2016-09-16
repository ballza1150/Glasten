package com.example.siamdecowork.glasten;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, widthEditText,
    heightEditText, detailEditText;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private RadioButton doorRadioButton, windowRadioButton;
    private String nameString, widthString, heighString,
            deetailString, typeString, imageString,
    imagePathString, imageFileString;
    private boolean imageABoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        nameEditText = (EditText) findViewById(R.id.editText);
        widthEditText = (EditText) findViewById(R.id.editText2);
        heightEditText = (EditText) findViewById(R.id.editText3);
        detailEditText = (EditText) findViewById(R.id.editText4);
        imageView = (ImageView) findViewById(R.id.imageView);
        radioGroup = (RadioGroup) findViewById(R.id.ragType);
        doorRadioButton = (RadioButton) findViewById(R.id.radioButton);
        windowRadioButton = (RadioButton) findViewById(R.id.radioButton2);

        //Image Conttroller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,
                        "โปรดเลือกรูปภาพ"), 1);

            }// OnClick
        });

    }// Main Method

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 1) && (resultCode == RESULT_OK)) {

            imageABoolean = false;

            // FindI path of Image

            Uri uri = data.getData();
            imagePathString = myFindPathImage(uri);

            Log.d("16SepV1", "imagePathString ==>" + imagePathString);

            //SetImage to imageView
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(uri));
                        imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }// if

    }// onActivity

    private String myFindPathImage(Uri uri) {

        String strResult = null;

        String[] strings = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings,
                null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
            int intIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            strResult = cursor.getString(intIndex);

        } else {
            strResult = uri.getPath();
        }


        return strResult;
    }

    public void clickSaveDataMain(View view) {

        //Get Value From Edit Text
        nameString = nameEditText.getText().toString().trim();
        widthString = widthEditText.getText().toString().trim();
        heighString = heightEditText.getText().toString().trim();
        deetailString = detailEditText.getText().toString().trim();

        //Check Space
        if (nameString.equals("") || widthString.equals("") ||
                heighString.equals("") || deetailString.equals("")) {
            // Have Space

            MyAlert myAlert = new MyAlert(this, R.drawable.doremon48,
                    "มีช่องว่าง", "กรุณา กรอก ช่องค่ะ");
            myAlert.myDialog();

        } else if (!(doorRadioButton.isChecked() || windowRadioButton.isChecked())) {
            // Un Check RadioButton
            MyAlert myAlert = new MyAlert(this, R.drawable.nobita48,
                    "ยังไม่เลือกชนิด", "กรุณาเลือก ชนิด ด้วยค่ะ");
            myAlert.myDialog();
        } else if (imageABoolean) {
            //Non Choose Image
            MyAlert myAlert = new MyAlert(this, R.drawable.bird48,
                    "ยังไม่ได้เลือกรูป", "กรุณาคลิกที่รูป และเลือกรูป");
            myAlert.myDialog();
        } else {
            // Complete Choose Image
        }

        Log.d("16SepV1", "imageBoolean ==> " + imageABoolean);

    }// Click Save

    public void ClickListDataMain(View view) {

    }


} // Main Class
