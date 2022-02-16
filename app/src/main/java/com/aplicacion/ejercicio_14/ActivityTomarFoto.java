package com.aplicacion.ejercicio_14;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityTomarFoto extends AppCompatActivity {

    ImageView objImagenView;
    Button btnTomarFotos, btnGuardarSqlite;
    String correntPhotoPath;

    static final int PETICCION_ACCESO_CAM = 100;
    static final int TAKE_PIC_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar_foto);

        objImagenView = (ImageView) findViewById(R.id.imageViewTomarFoto);
        btnGuardarSqlite = (Button) findViewById(R.id.btnGuardarSql);
        btnTomarFotos = (Button) findViewById(R.id.btnTomarFoto);

        btnTomarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });
    }


    private void permisos(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PETICCION_ACCESO_CAM);
        }else{

            tomarFoto();
        }
    }

    private void tomarFoto(){
        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePhoto.resolveActivity(getPackageManager()) != null){

            startActivityForResult(takePhoto, TAKE_PIC_REQUEST);
        }


    }

    // Metodos que son override
    /*********************************************************************************************************************************************************/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PETICCION_ACCESO_CAM){

            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                tomarFoto();
            }
        }else{

            Toast.makeText(getApplicationContext(), "La aplicacion nesecita permisos de acceso a camara", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TAKE_PIC_REQUEST && resultCode == RESULT_OK){

            Bundle bundle = data.getExtras();

            Bitmap image = (Bitmap) bundle.get("data");

            objImagenView.setImageBitmap(image);
        }
    }
}