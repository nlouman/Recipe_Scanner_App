package com.example.recipe_scanner_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DisplayRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);
    }

    public void stringtopdf(String data) {
        String extstoragedir = Environment.getExternalStorageDirectory().toString();
        File fol = new File(extstoragedir, "pdf");
        File folder = new File(fol, "pdf");
        if (!folder.exists()) {
            boolean bool = folder.mkdir();
        }
        try {
            final File file = new File(folder, "sample.pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);


            PdfDocument document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new
                    PdfDocument.PageInfo.Builder(100, 100, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();

            canvas.drawText(data, 10, 10, paint);


            document.finishPage(page);
            document.writeTo(fOut);
            document.close();

        } catch (IOException e) {
            Log.i("error", e.getLocalizedMessage());
        }
    }
}