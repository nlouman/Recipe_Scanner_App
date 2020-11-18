package com.example.recipe_scanner_app.Main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.recipe_scanner_app.Displayer.DisplayRecipeActivity;
import com.example.recipe_scanner_app.R;
import com.example.recipe_scanner_app.Scanner.ScanRecipeActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    //For getting data from firebase
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Bitmap> recipePreviewFromFB;
    private ArrayList<PdfDocument> recipeFileFromFB;

    // For tool bar
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton scanRecipeButton = findViewById(R.id.scanRecipeButton);

        //For getting data from firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //For using recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MenuRecyclerAdapter menuRecyclerAdapter = new MenuRecyclerAdapter(recipeList);
        recyclerView.setAdapter(menuRecyclerAdapter);

        // Register Callback (for checking network connection)
        CheckNetwork network = new CheckNetwork(getApplicationContext());
        network.registerNetworkCallback();

/*        // Beginnings of a tool bar
        NavHostFragment navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
*/
        // Check network connection
        if (GlobalVariables.isNetworkConnected){
            getDataFromFireStore(menuRecyclerAdapter);
            System.out.println("from FireStore...");
        } else {
            getDataFromSQLDatabase(menuRecyclerAdapter);
            System.out.println("from SQL Database...");
        }

        //OnClickListener for scanning a new recipe
        scanRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanRecipeActivity.class);
                startActivity(intent);
            }
        });
    }

    // For adding app bar support
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }



    //If a recipe is selected, start the DisplayRecipeActivity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.recipe_preview_view || item.getItemId() == R.id.recipe_name_view) {
            Intent intent = new Intent(MainActivity.this, DisplayRecipeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    // Get recipe data from FireStore
    public void getDataFromFireStore(MenuRecyclerAdapter menuRecyclerAdapter) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        System.out.println(firebaseUser.getEmail());
        CollectionReference collectionReference = firebaseFirestore.collection("Users").document(firebaseUser.getEmail()).collection("Recipes");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

                if (value != null) {
                    for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                        Map<String, Object> data = documentSnapshot.getData();

                        String recipeName = (String) data.get("recipeName");
                        String urlPreview = (String) data.get("downloadUrlPreview");
                        String urlFile = (String) data.get("downloadUrlFile");

                        recipeList.add(new Recipe(recipeName, urlPreview, urlFile));

                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                recipePreviewFromFB.add(bitmap);
                                menuRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                e.printStackTrace();
                                System.out.println("here!");

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        };
                        Picasso.get().load(urlPreview).into(target);


                    }
                }
            }
        });
    }

    public void getDataFromSQLDatabase(MenuRecyclerAdapter menuRecyclerAdapter) {
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Recipes", MODE_PRIVATE, null);

            Cursor cursor = database.rawQuery("SELECT * FROM Recipes", null);
            int nameIx = cursor.getColumnIndex("recipeNames");
            int previewIx = cursor.getColumnIndex("recipePreviews");
            int fileIx = cursor.getColumnIndex("recipeFiles");

            Recipe recipe = null;
            while (cursor.moveToNext()) {
                recipe.recipeName = cursor.getString(nameIx);
                Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(previewIx), 0, cursor.getBlob(previewIx).length);
                recipe.recipePreview = bitmap;
                //PdfDocument pdfDocument = File;
                //FileInputStream = getFileStreamPath(cursor.getString(fileIx));
                //File file =
                //recipe.recipeFile = ;
                menuRecyclerAdapter.notifyDataSetChanged();
            }

            cursor.close();

        } catch (Exception e) {
            System.out.println("catch");
            e.printStackTrace();
        }
    }
}