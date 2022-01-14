package com.example.myrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.example.myrecipe.adapter.SearchAdapter;
import com.example.myrecipe.model.SearchModel;

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

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.recycler_search_view)
    RecyclerView recycler_view;

    SearchAdapter mAdapter;

    static String myJSONStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

       // actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        myJSONStr = loadJSONFromRaw();

        //Recycler_view
        ButterKnife.bind(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setNestedScrollingEnabled(false);
        mAdapter = new SearchAdapter(this, prepareData());
        recycler_view.setAdapter(mAdapter);
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
            case android.R.id.home :
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static List<SearchModel> prepareData() {
        List<SearchModel> searchList = new ArrayList<>();
        try {

            JSONArray iJSONArray = new JSONArray(myJSONStr);
            for (int i = 0; i < iJSONArray.length(); i++) {
                //Create a temp object of the ingredient model class
                SearchModel searchModel = new SearchModel();

                //Get ingredient JSON object node
                JSONObject jsonObject = iJSONArray.getJSONObject(i);

                //Get name and image of ingredients
                searchModel.setName(jsonObject.getString("TranslatedRecipeName"));
                //searchModel.setImage(jsonObject.getString("Image_URL"));
                //searchModel.setImage(jsonObject.getString("image"));
                searchList.add(searchModel);



            }
            return searchList;
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
            InputStream is = getResources().openRawResource(R.raw.indianfooddataset);
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