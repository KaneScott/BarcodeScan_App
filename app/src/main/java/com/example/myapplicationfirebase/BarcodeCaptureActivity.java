package com.example.myapplicationfirebase;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

public class BarcodeCaptureActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_capturenew);
        takePictureIntent();
    }

    private void takePictureIntent(){
        if(checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_PERMISSION_CODE);
        }
        else{
            Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePic, CAMERA_REQUEST);
        }


    }

    private void useBarcode(List<FirebaseVisionBarcode> barcodes ){
        for(FirebaseVisionBarcode barcode: barcodes){
            Rect bounds = barcode.getBoundingBox();
            Point[] corners = barcode.getCornerPoints();
            String rawVal = barcode.getRawValue();
            int valType = barcode.getValueType();
            TextView barcodeMessage = (TextView)findViewById(R.id.barcode_data);
            switch(valType){
                case FirebaseVisionBarcode.TYPE_PRODUCT:
                    String displayVal = barcode.getDisplayValue();
                    barcodeMessage.setText(displayVal);
                    break;
                default:
                    barcodeMessage.setText("IMAGE TAKEN");
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int reqCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(reqCode, permissions, grantResults);
        if(reqCode == MY_CAMERA_PERMISSION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else{
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        TextView barcodeMessage = (TextView)findViewById(R.id.barcode_data);
        barcodeMessage.setText("Activity Resolved");
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){

            barcodeMessage.setText("IMAGE TAKEN!!!!!!!");
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ImageView barcodeImage = (ImageView)findViewById(R.id.barcode_image);
            barcodeImage.setImageBitmap(imageBitmap);

            FirebaseVisionBarcodeDetectorOptions barcodeOptions =
                    new FirebaseVisionBarcodeDetectorOptions.Builder()
                            .setBarcodeFormats(
                                    FirebaseVisionBarcode.FORMAT_AZTEC,
                                    FirebaseVisionBarcode.FORMAT_QR_CODE,
                                    FirebaseVisionBarcode.FORMAT_EAN_13)
                            .build();

            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);

//            FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
//                    .getVisionBarcodeDetector();
//            detector.
           // Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image);
//            result.addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
//                @Override
//                public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
//                    //useBarcode(firebaseVisionBarcodes);
//                    TextView barcodeMessage = (TextView)findViewById(R.id.read_barcode);
//                    barcodeMessage.setText("Succeeded to read barcode");
//                }
//            });
//            result.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    TextView barcodeMessage = (TextView)findViewById(R.id.read_barcode);
//                    barcodeMessage.setText("Failed to read barcode");
//                }
//            });

        }
    }
}
