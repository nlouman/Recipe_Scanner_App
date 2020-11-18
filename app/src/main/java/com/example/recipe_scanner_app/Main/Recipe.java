package com.example.recipe_scanner_app.Main;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;

public class Recipe {
    public String recipeName;
    public String urlPreview;
    public String urlFile;
    public Bitmap recipePreview;
    public PdfDocument recipeFile;

    public Recipe(String name, String urlPreview, String urlFile) {
        this.recipeName = name;
        this.urlPreview = urlPreview;
        this.urlFile = urlFile;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public Bitmap getRecipePreview() {
        return recipePreview;
    }

    public void setRecipePreview(Bitmap recipePreview) {
        this.recipePreview = recipePreview;
    }

    public PdfDocument getRecipeFile() {
        return recipeFile;
    }

    public void setRecipeFile(PdfDocument recipeFile) {
        this.recipeFile = recipeFile;
    }
}
