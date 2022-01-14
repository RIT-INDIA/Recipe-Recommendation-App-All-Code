package com.example.myrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrecipe.adapter.MaharashtrianFoodAdapter;
import com.example.myrecipe.adapter.NorthIndianFoodAdapter;
import com.example.myrecipe.adapter.PopularFoodAdapter;
import com.example.myrecipe.adapter.SouthIndianFoodAdapter;
import com.example.myrecipe.model.MaharashtraFood;
import com.example.myrecipe.model.NorthFood;
import com.example.myrecipe.model.PopularFood;
import com.example.myrecipe.model.SouthFood;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    RecyclerView popularRecycler, maharashtraRecycler,southRecycler,northRecycler;
    FloatingActionButton chat,ingredients,search;

    PopularFoodAdapter popularFoodAdapter;
    MaharashtrianFoodAdapter maharashtrianFoodAdapter;
    SouthIndianFoodAdapter southIndianFoodAdapter;
    NorthIndianFoodAdapter northIndianFoodAdapter;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ImageView drawerimg;


    BottomAppBar bottomAppBar;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        //getSupportActionBar().hide(); // hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        drawerLayout=findViewById(R.id.drower_layout);
        navigationView=findViewById(R.id.nav_view);


        //bottomAppBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.nav_share:
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recipe Suggestion App");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Recipe Suggestion app is here,download it and join us! http://play.google.com/store/apps/details?id=snehal.rutuja.geetu.ritmusiccell");
                        Intent i = Intent.createChooser(sharingIntent, "Share via");
                        startActivity(i);
                        break;
                    case R.id.nav_rate:
                        try {
                            Uri uri = Uri.parse("market://details?id=ravindrascompiler.rit.user.compilemaster&hl=en");
                            Intent i1 = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(i1);
                        } catch (ActivityNotFoundException e) {
                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                            Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                            startActivity(i2);
                        }
                        break;
                    case R.id.nav_about:
                        Intent i1=new Intent(Home.this,AboutusActivity.class);
                        startActivity(i1);
                        break;
                    default:
                        break;
                }
                return false;
            }

        });

        setPopularRecycler(polulardata());
        setMaharashtraRecycler(MaharashtraFood());
        setSouthRecycler(SouthFood());
        setNorthRecycler(NorthFood());

        chat=findViewById(R.id.floatingActionButton);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Home.this,Chatbot.class);
                startActivity(i);
            }
        });

        ingredients=findViewById(R.id.floatingActionButton2);
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Ingredients.class);
                startActivity(i);
            }
        });

        drawerimg=findViewById(R.id.imageView3);
        drawerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

       search = findViewById(R.id.floatingActionButton3);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        bottomAppBar = findViewById(R.id.bottomAppBar2);
        bottomAppBar.setHideOnScroll(true);
        setSupportActionBar(bottomAppBar);

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home :
                        return true;
                    case R.id.navigation_search :
                        Intent intent = new Intent(Home.this,SearchActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press 'Back' again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home :
                return true;
            case R.id.navigation_search :
                Intent intent = new Intent(Home.this,SearchActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void openDrawer(DrawerLayout drawerLayout)
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void setPopularRecycler(List<PopularFood> popularFoodList) {

        popularRecycler = findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        popularFoodAdapter = new PopularFoodAdapter(this, popularFoodList);
        popularRecycler.setAdapter(popularFoodAdapter);

    }

    private void setMaharashtraRecycler(List<MaharashtraFood> maharashtraFoodList) {

        maharashtraRecycler = findViewById(R.id.asia_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        maharashtraRecycler.setLayoutManager(layoutManager);
        maharashtrianFoodAdapter = new MaharashtrianFoodAdapter(this, maharashtraFoodList);
        maharashtraRecycler.setAdapter(maharashtrianFoodAdapter);

    }

    public void setSouthRecycler(List<SouthFood> southFoodList) {

        southRecycler = findViewById(R.id.south_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        southRecycler.setLayoutManager(layoutManager);
        southIndianFoodAdapter = new SouthIndianFoodAdapter(this,southFoodList);
        southRecycler.setAdapter(southIndianFoodAdapter);
    }

    public void setNorthRecycler(List<NorthFood> northFoodList) {

        northRecycler = findViewById(R.id.north_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        northRecycler.setLayoutManager(layoutManager);
        northIndianFoodAdapter = new NorthIndianFoodAdapter(this,northFoodList);
        northRecycler.setAdapter(northIndianFoodAdapter);
    }

    public List<PopularFood> polulardata(){
       List<PopularFood> popularFoodList = new ArrayList<>();

       String myJSONStr = loadJSONFromRawPopular();
       try {

           //Get root JSON object node

           JSONObject rootJSONObject = new JSONObject(myJSONStr);
           //Get ingredient_list array node
           JSONArray iJSONArray = rootJSONObject.getJSONArray("Popular");
           for (int i = 0; i < iJSONArray.length(); i++) {
               //Create a temp object of the ingredient model class
               //Get ingredient JSON object node

               PopularFood popularFood=new PopularFood();

               JSONObject jsonObject = iJSONArray.getJSONObject(i);

               //Get name of ingredients

               popularFood.setName(jsonObject.getString("Dish"));
               popularFood.setImageUrl(jsonObject.getString("URL"));
               popularFoodList.add(popularFood);


           }
return popularFoodList;

       } catch (JSONException e) {
           Log.d("catch","catched");
           e.printStackTrace();

       }
       return null;
    }

    public List<MaharashtraFood> MaharashtraFood(){

        List<MaharashtraFood> maharashtraFoodList = new ArrayList<>();

        String myJSONStr = loadJSONFromRawMaharashtrian();
        try {

            //Get root JSON object node

            JSONObject rootJSONObject = new JSONObject(myJSONStr);
            //Get ingredient_list array node
            JSONArray iJSONArray = rootJSONObject.getJSONArray("Maharashtrian");
            for (int i = 0; i < iJSONArray.length(); i++) {
                //Create a temp object of the ingredient model class
                //Get ingredient JSON object node

                MaharashtraFood maharashtraFood =new MaharashtraFood();

                JSONObject jsonObject = iJSONArray.getJSONObject(i);

                //Get name of ingredients

                maharashtraFood.setName(jsonObject.getString("Recipe"));
                maharashtraFood.setImageUrl(jsonObject.getString("ImageURL"));
                maharashtraFoodList.add(maharashtraFood);


            }
            return maharashtraFoodList;

        } catch (JSONException e) {
            Log.d("catch","catched");
            e.printStackTrace();

        }
        return null;
    }

    public List<SouthFood> SouthFood()
    {
        List<SouthFood> southFoodList = new ArrayList<>();

        String myJSONStr = loadJSONFromRawSouth();
        try {

            //Get root JSON object node

            JSONObject rootJSONObject = new JSONObject(myJSONStr);
            //Get ingredient_list array node
            JSONArray iJSONArray = rootJSONObject.getJSONArray("South");
            for (int i = 0; i < iJSONArray.length(); i++) {
                //Create a temp object of the ingredient model class
                //Get ingredient JSON object node

                SouthFood southFood =new SouthFood();

                JSONObject jsonObject = iJSONArray.getJSONObject(i);

                //Get name of ingredients

                southFood.setName(jsonObject.getString("Recipe"));
                southFood.setImageUrl(jsonObject.getString("ImageURL"));
                southFoodList.add(southFood);


            }
            return southFoodList;

        } catch (JSONException e) {
            Log.d("catch","catched");
            e.printStackTrace();

        }
        return null;

    }

    public List<NorthFood> NorthFood()
    {
        List<NorthFood> northFoodList = new ArrayList<>();

        String myJSONStr = loadJSONFromRawNorth();
        try {

            //Get root JSON object node

            JSONObject rootJSONObject = new JSONObject(myJSONStr);
            //Get ingredient_list array node
            JSONArray iJSONArray = rootJSONObject.getJSONArray("North");
            for (int i = 0; i < iJSONArray.length(); i++) {
                //Create a temp object of the ingredient model class
                //Get ingredient JSON object node

               NorthFood northFood =new NorthFood();

                JSONObject jsonObject = iJSONArray.getJSONObject(i);

                //Get name of ingredients

                northFood.setName(jsonObject.getString("Recipe"));
                northFood.setImageUrl(jsonObject.getString("ImageURL"));
                northFoodList.add(northFood);


            }
            return northFoodList;

        } catch (JSONException e) {
            Log.d("catch","catched");
            e.printStackTrace();

        }
        return null;

    }
    public String loadJSONFromRawPopular()
    {
        String json;
        try
        {
            InputStream is = getResources().openRawResource(R.raw.popularfood);
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public String loadJSONFromRawMaharashtrian()
    {
        String json;
        try
        {
            InputStream is = getResources().openRawResource(R.raw.maharashtrian);
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public String loadJSONFromRawNorth()
    {
        String json;
        try
        {
            InputStream is = getResources().openRawResource(R.raw.north);
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public String loadJSONFromRawSouth()
    {
        String json;
        try
        {
            InputStream is = getResources().openRawResource(R.raw.south);
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}