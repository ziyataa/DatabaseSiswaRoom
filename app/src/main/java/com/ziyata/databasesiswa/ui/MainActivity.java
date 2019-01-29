package com.ziyata.databasesiswa.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ziyata.databasesiswa.R;
import com.ziyata.databasesiswa.adapter.KelasAdapter;
import com.ziyata.databasesiswa.db.SiswaDatabase;
import com.ziyata.databasesiswa.model.KelasModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rvKelas)
    RecyclerView rvKelas;

    // TODO 1 Membuat variable

    private SiswaDatabase siswaDatabase;
    private List<KelasModel> kelasModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO 2 Mensetting variable yang di butuhkan
        // Membuat object database
        siswaDatabase = SiswaDatabase.createDatbase(this);

        // Membuat object List
        kelasModelList = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahKelasActivity.class));
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // Mengosongkan List agar dipastikan list dapat diisi dengan data yang paling baru
        kelasModelList.clear();

        // Mengambil data dari SQLite
        getData();
        // Mensetting dan proses menampilkan data ke recycleView
        rvKelas.setLayoutManager(new GridLayoutManager(this,2));
        rvKelas.setAdapter(new KelasAdapter(this, kelasModelList));
    }

    private void getData() {
        // Operasi mengambil data dari database SQLite
        kelasModelList = siswaDatabase.kelasDao().select();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
