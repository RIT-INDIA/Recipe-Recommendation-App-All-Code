package com.example.myrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myrecipe.adapter.StudentAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Ingredients extends AppCompatActivity implements View.OnClickListener{
    public static StringBuilder listString1;
    public static String stringSingle;
    String name = "";
    private ImageView dairy,vegetables,fruits,baking,condiments,meats,seafoods,liquids,nuts;
    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        bottomAppBar = findViewById(R.id.bottomAppBar2);
        bottomAppBar.setHideOnScroll(true);
        setSupportActionBar(bottomAppBar);

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home :
                        Intent i=new Intent(Ingredients.this,Home.class);
                        startActivity(i);
                        return true;
                    case R.id.navigation_search :
                        Intent intent = new Intent(Ingredients.this,SearchActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        dairy = findViewById(R.id.dairy);
        dairy.setOnClickListener(this);

        vegetables = findViewById(R.id.vegetables);
        vegetables.setOnClickListener(this);

        fruits = findViewById(R.id.fruits);
        fruits.setOnClickListener(this);

        baking = findViewById(R.id.baking);
        baking.setOnClickListener(this);

        condiments = findViewById(R.id.condiments);
        condiments.setOnClickListener(this);

        meats = findViewById(R.id.meats);
        meats.setOnClickListener(this);

        seafoods = findViewById(R.id.seafoods);
        seafoods.setOnClickListener(this);

        liquids = findViewById(R.id.liquids);
        liquids.setOnClickListener(this);

        nuts = findViewById(R.id.nuts);
        nuts.setOnClickListener(this);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               StringBuilder listString=new StringBuilder();
               listString1 = new StringBuilder();

                for (int i = 0; i< StudentAdapter.finallist.size(); i++){
                    listString.append(StudentAdapter.finallist.get(i));
                    listString.append("\n");
                    listString1.append(StudentAdapter.finallist.get(i));
                    listString1.append(" ");

                }
                stringSingle = listString1.toString();
                Log.d("string", stringSingle);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Ingredients.this);
                alertDialogBuilder.setTitle("Ingredients List");
                alertDialogBuilder.setMessage(listString);
                alertDialogBuilder.setCancelable(true);

                alertDialogBuilder.setPositiveButton("See Recipe", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        Intent i=new Intent(Ingredients.this,ShowRecepieActivity.class);
                        StudentAdapter.finallist.clear();
                        startActivity(i);
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        AlertDialog mDialog =alertDialogBuilder.create();
                        mDialog.cancel();
                    }
                });
                AlertDialog mDialog =alertDialogBuilder.create();
                mDialog.show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StudentAdapter.finallist.clear();
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
                Intent intent2 = new Intent(Ingredients.this,Home.class);
                StudentAdapter.finallist.clear();
                startActivity(intent2);
                return true;
            case R.id.navigation_search :
                Intent intent = new Intent(Ingredients.this,SearchActivity.class);
                StudentAdapter.finallist.clear();
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        Intent i = new Intent(this, Ingredientslist.class);
        Bundle bundle = new Bundle();

        switch (id)
        {
            case R.id.dairy:
                name = "Dairy";
                bundle.putString("key",name);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.vegetables:
                name = "Vegetables";
                bundle.putString("key",name);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.fruits:
                name = "Fruits";
                bundle.putString("key",name);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.baking:
                name = "Baking & Grains";
                bundle.putString("key",name);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.condiments:
                name = "Condiments";
                bundle.putString("key",name);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.meats:
                name = "Meats";
                bundle.putString("key",name);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.seafoods:
                name = "Sea Foods";
                bundle.putString("key",name);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.liquids:
                name = "Liquids";
                bundle.putString("key",name);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.nuts:
                name = "Nuts";
                bundle.putString("key",name);
                i.putExtras(bundle);
                startActivity(i);
                break;
        }
    }

}