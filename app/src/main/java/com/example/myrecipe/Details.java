package com.example.myrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class Details extends AppCompatActivity {

    ImageView foodimg,backimg,shareimg;
    TextView cooking_time,servings,cuisine,foodtype,recipename,ingredients,procedure;
    Intent intent;
    
    String send,share,recipename2;
    StringBuffer instruction = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);



        foodimg=findViewById(R.id.imageView5);
        backimg=findViewById(R.id.back_button);
        shareimg=findViewById(R.id.share);

        cooking_time=findViewById(R.id.textView3);
        servings=findViewById(R.id.textView5);
        cuisine=findViewById(R.id.textView6);

        foodtype=findViewById(R.id.textView10);
        recipename=findViewById(R.id.textView11);

        ingredients=findViewById(R.id.textView13);
        procedure=findViewById(R.id.textView15);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");


                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,send);

                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Recipe is here,You can see it on"+send+share);
                Intent i = Intent.createChooser(sharingIntent, "Share via");
                startActivity(i);


            }
        });

        intent=getIntent();
        Bundle b1=intent.getExtras();
        recipename2 = b1.getString("name");

        loadDetails(recipename2);


    }



    public void loadDetails(String name){
        String json;
        try
        {
            InputStream is = getResources().openRawResource(R.raw.indianfooddataset);
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray=new JSONArray(json);

            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject obj=jsonArray.getJSONObject(i);
                String recipe=obj.optString("TranslatedRecipeName");

                if(recipe.contains(name))
                {

//

                    recipename.setText(obj.getString("TranslatedRecipeName"));
                    ingredients.setText(obj.getString("TranslatedIngredients"));
                    cooking_time.setText(Integer.toString(obj.getInt("CookTimeInMins")));
                    servings.setText(Integer.toString(obj.getInt("Servings")));
                    cuisine.setText(obj.getString("Cuisine"));
                    foodtype.setText(obj.getString("Diet"));
                    //procedure.setText(obj.getString("TranslatedInstructions"));

                    String temp = obj.getString("TranslatedInstructions");
                    String str[] = temp.split("\\.");

                    for (int k = 0; k < str.length; k++) {
                        instruction.append(k+1 +". "+ str[k] +".\n\n");
                    }
                    procedure.setText(instruction);

                    share=obj.getString("URL");
                    send=obj.getString("TranslatedRecipeName");
                    Glide.with(getApplicationContext()).load(obj.getString("Image_URL")).placeholder(R.drawable.asiafood1).into(foodimg);
                    Log.d("Check","cheking");
                    break;
                }
            }

        } catch (IOException ex)
        {
            ex.printStackTrace();

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

  /*  @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.share :
                Toast.makeText(getApplicationContext(),send,Toast.LENGTH_LONG).show();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,send);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Recipe is here,You can see it on "+share);
                Intent i = Intent.createChooser(sharingIntent, "Share via");
                startActivity(i);
                break;
            case R.id.back_button:
                this.finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }*/
}