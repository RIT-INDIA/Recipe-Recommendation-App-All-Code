package com.example.myrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.myrecipe.adapter.RecycleViewAdapter;

import java.util.List;

public class ShowRecepieActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    RecycleViewAdapter recycleViewAdapter;
    TextView textView;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);



//    ArrayAdapter<PyObject> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_recepie);


        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recycler);
        textView = findViewById(R.id.recipe);



        new load().execute();


    }



    public class load extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            if(!Python.isStarted())
            {
                Python.start(new AndroidPlatform(ShowRecepieActivity.this));
            }
            Python python = Python.getInstance();
            PyObject pyObject = python.getModule("script");
            List<PyObject> pyObjectList=pyObject.callAttr("prediction",Ingredients.stringSingle).asList();
            return pyObjectList;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            textView.setText("Loading...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            List<PyObject> pyObjectList1 = (List<com.chaquo.python.PyObject>) o;
            recyclerView.setLayoutManager(layoutManager);
            recycleViewAdapter = new RecycleViewAdapter(ShowRecepieActivity.this,pyObjectList1);
            Log.d("size", String.valueOf(pyObjectList1.size()));
            recyclerView.setAdapter(recycleViewAdapter);


//            arrayAdapter = new ArrayAdapter<PyObject>(ShowRecepieActivity.this, android.R.layout.simple_expandable_list_item_1, pyObjectList1);
//            listView.setAdapter(arrayAdapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String itemstr =String.valueOf(arrayAdapter.getItem(position));
//                    Bundle bundle = new Bundle();
//                    bundle.putString("name",itemstr);
//                    Intent intent = new Intent(ShowRecepieActivity.this, Details.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    Toast.makeText(ShowRecepieActivity.this, itemstr, Toast.LENGTH_SHORT).show();

//                }
//            });
            progressBar.setVisibility(View.GONE);
            textView.setText("Recipe Displayed!!");
        }
    }

    @Override
    public void onBackPressed() {
        finish();

        super.onBackPressed();
    }
}