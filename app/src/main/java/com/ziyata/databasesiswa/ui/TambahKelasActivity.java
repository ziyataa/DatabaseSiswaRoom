package com.ziyata.databasesiswa.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ziyata.databasesiswa.R;
import com.ziyata.databasesiswa.db.SiswaDatabase;
import com.ziyata.databasesiswa.model.KelasModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahKelasActivity extends AppCompatActivity {

    // Membutter knife
    @BindView(R.id.edtNamaKelas)
    EditText edtNamaKelas;
    @BindView(R.id.edtNamaWali)
    EditText edtNamaWali;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    // TODO 1  Membuat Variable yang dibutuhkan
    private SiswaDatabase siswaDatabase;

    private String namaKelas, namaWali;
    private int idKelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kelas);
        ButterKnife.bind(this);

        // TODO 2 Mensetting judul activity
        setTitle("Add Kelas");

        // TODO 3 Membuat object database
        siswaDatabase = SiswaDatabase.createDatbase(this);
    }

    @OnClick(R.id.btnSimpan)
    public void onViewClicked() {
        // TODO 4 Mengambil data input data dari user
        getData();

        // TODO Proses Menyimpan data ke SQLite
        saveData();

        // TODO untuk membersihkan data
        clearData();

    }

    private void clearData() {
        edtNamaKelas.setText("");
        edtNamaWali.setText("");
    }

    private void saveData() {
        /**
         * dinamakan kelas models karena dia list gunanya list yaitu untuk menginsert
         * sedangkan kalo kelas model adalah kelas
         */


        Toast.makeText(this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();

        // Membuat object KelasModel untuk menampung data
        KelasModel kelasModel = new KelasModel();

        // Memasukkan data ke dalam kelas model
        kelasModel.setNama_kelas(namaKelas);
        kelasModel.setNama_wali(namaWali);

        // Perintah untuk melakukan operasi Insert menggunakan siswaDatabase
        siswaDatabase.kelasDao().insert(kelasModel);
        finish();
    }

    private void getData() {
        namaKelas = edtNamaKelas.getText().toString();
        namaWali = edtNamaWali.getText().toString();
    }
}
