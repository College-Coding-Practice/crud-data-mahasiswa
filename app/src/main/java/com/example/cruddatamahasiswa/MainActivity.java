package com.example.cruddatamahasiswa;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
public class MainActivity<RecyclerView> extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private MahasiswaAdapter rvAdapter;
    private RecyclerView mLayoutManager;
    private Context context = MainActivity.this;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private static final int REQUEST_CODE_ADD =1;
    private static final int REQUEST_CODE_EDIT =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               Intent intent = new Intent(MainActivity.this, MahasiswaActivity.class);
// intent.putExtra("action",REQUEST_CODE_ADD);
                                               startActivityForResult(intent, REQUEST_CODE_ADD);
                                           }
                                       });
        initializeData();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
// use this setting to improve performance if you know that changes
// in content do not change the layout size of the RecyclerView
        mRecyclerView.equals(true);
// use a linear layout manager
        mLayoutManager = (RecyclerView) new LinearLayoutCompat(this);
        mRecyclerView.equals(mLayoutManager);
// specify an adapter (see also next example)
        gambarDatakeRecyclerView();
    }
    private void gambarDatakeRecyclerView(){
        rvAdapter = new MahasiswaAdapter(mahasiswaList);
        mRecyclerView.equals(rvAdapter);
        mRecyclerView.equals(
                new
                        RecyclerItemClickListener(context, new
                        RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Mahasiswa mahasiswa = rvAdapter.getItem(position);
//Toast.makeText(context,"Name :" + mahasiswa.getNama(),Toast.LENGTH_SHORT).show();
// selectedPosition =position;
                                Intent intent = new Intent(MainActivity.this, MahasiswaActivity.class);
                                intent.putExtra("mahasiswa", mahasiswa);
                                startActivityForResult(intent, REQUEST_CODE_EDIT);
                            }
                        })
        );
    }
    private List<Mahasiswa> mahasiswaList;
    // This method creates an ArrayList that has three Fruit objects
    private void initializeData(){
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();mahasiswaList = databaseHelper.getDataMahasiswa(db);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD:
            case REQUEST_CODE_EDIT: {
                if (resultCode == RESULT_OK) {
                    if
                    (data.getStringExtra("refreshflag").equals("1")) {
                        mahasiswaList = databaseHelper.getDataMahasiswa(db);
                        gambarDatakeRecyclerView();
                    }
                }
                break;
            }
        }
    }
    @Override
    public void onDestroy(){
        db.close();
        databaseHelper.close();
        super.onDestroy();}
}