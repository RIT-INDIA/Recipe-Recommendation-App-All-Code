package com.example.myrecipe;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.adapter.StudentAdapter;
import com.example.myrecipe.model.StudentModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class Ingredientslist extends AppCompatActivity {

    String category;
    FloatingActionButton floatingActionButton;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.cbSelectAll)
    CheckBox cbSelectAll;

    @BindView(R.id.tvSelect)
    TextView tvSelect;

    StudentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientslist);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        /* ----------popup code ---------*/
        /**
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);
        **/

        floatingActionButton=findViewById(R.id.floatingActionButton2);

        Bundle b = getIntent().getExtras();
        category = b.getString("key","Thank");

        //Recycler_view
        ButterKnife.bind(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setNestedScrollingEnabled(false);
        mAdapter = new StudentAdapter(this, prepareData());
        recycler_view.setAdapter(mAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder listString=new StringBuilder();
                for (int i=0;i<StudentAdapter.finallist.size();i++){
                    listString.append(StudentAdapter.finallist.get(i));
                    listString.append(",");
                }
                Toast.makeText(getApplicationContext(),listString,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);  //option menu

        MenuItem searchViewItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) searchViewItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                mAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText)
            {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnCheckedChanged(R.id.cbSelectAll)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  //clear all
        if (mAdapter != null) {
            mAdapter.toggleSelection(isChecked);
        }
    }


    public List<StudentModel> prepareData() {
            List<StudentModel> studentList = new ArrayList<>();
            String myJSONStr = loadJSONFromRaw();
            try {
                //Get root JSON object node
                JSONObject rootJSONObject = new JSONObject(myJSONStr);
                //Get ingredient_list array node
                JSONArray iJSONArray = rootJSONObject.getJSONArray(category);
                for (int i = 0; i < iJSONArray.length(); i++) {
                    //Create a temp object of the ingredient model class
                    StudentModel aIngredient = new StudentModel();

                    //Get ingredient JSON object node
                    JSONObject jsonObject = iJSONArray.getJSONObject(i);

                    //Get name and image of ingredients
                    aIngredient.setName(jsonObject.getString("name"));
                    aIngredient.setImage(jsonObject.getString("image"));
                    studentList.add(aIngredient);
                }
                return studentList;
                } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
    }

    public String loadJSONFromRaw()
    {
        String json;
        try
        {
            InputStream is = getResources().openRawResource(R.raw.ingredients_list);
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