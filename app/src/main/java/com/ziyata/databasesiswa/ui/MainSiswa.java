package com.ziyata.databasesiswa.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ziyata.databasesiswa.R;
import com.ziyata.databasesiswa.adapter.SiswaAdapter;
import com.ziyata.databasesiswa.db.Constant;
import com.ziyata.databasesiswa.db.SiswaDatabase;
import com.ziyata.databasesiswa.model.SiswaModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainSiswa extends AppCompatActivity {

    @BindView(R.id.rvSiswa)
    RecyclerView rvSiswa;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    // TODO 1 Membuat variable yang kita butuhkan
    private SiswaDatabase siswaDatabase;
    private List<SiswaModel> siswaModelList;
    private Bundle bundle;
    private int id_kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_siswa);
        ButterKnife.bind(this);

        // Menangkap data dari activiity sebelumnya
        bundle = getIntent().getExtras();

        if (bundle != null) {
            // Menangkap data id_kelas ke dalam variable
            id_kelas = bundle.getInt(Constant.KEY_ID_KELAS);
            // Set Title dengan mengginakan nama kelas yang di tangkap dari activity sebelumnya
            setTitle(bundle.getString(Constant.KEY_NAMA_KELAS));
        }

        // Membuat database object
        siswaDatabase = SiswaDatabase.createDatbase(this);

        // Membuat object list
        siswaModelList = new ArrayList<>();

        setTitle("Data Siswa");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // Menghapus isi data di dalam list
        siswaModelList.clear();

        // Mengambil data dan mengisinya
        getData();

        // Mensetting adapter untuk menampilkan datanya
        // Mensetting garis bawah
        rvSiswa.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Setting LinearLayout
        rvSiswa.setLayoutManager(new LinearLayoutManager(this));

        // Set Adapter
        rvSiswa.setAdapter(new SiswaAdapter(this, siswaModelList));
    }

    private void getData() {
        // Operasi mengambil data yang ada di dalam SQLite menggunakan select dengan parameter id_kelas
        // untuk mengambil data siswa yang sesuai dengan kelas yang diinginkan
        siswaModelList = siswaDatabase.kelasDao().selectSiswa(id_kelas);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(this,TambahSiswaActivity.class).putExtra(Constant.KEY_ID_KELAS, id_kelas));
    }
}
