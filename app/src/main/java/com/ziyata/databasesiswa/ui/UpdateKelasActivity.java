package com.ziyata.databasesiswa.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ziyata.databasesiswa.R;
import com.ziyata.databasesiswa.db.Constant;
import com.ziyata.databasesiswa.db.SiswaDatabase;
import com.ziyata.databasesiswa.model.KelasModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateKelasActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaKelas)
    EditText edtNamaKelas;
    @BindView(R.id.edtNamaWali)
    EditText edtNamaWali;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    // Membuat bundle
    private Bundle bundle;

    // Membuat variable List
    private List<KelasModel> kelasModelList;

    // Membuat variable database
    private SiswaDatabase siswaDatabase;

    private int id_kelas;
    private String nama_kelas, nama_wali;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_kelas);
        ButterKnife.bind(this);

        // Mengeset title
        setTitle("Update data kelas");

        // Menangkap data dari activity sebelumnya
        bundle = getIntent().getExtras();

        // Buat object list
        kelasModelList = new ArrayList<>();

        // Buat object database
        siswaDatabase = SiswaDatabase.createDatbase(this);

        // Menampilkan data sebelumnya ke layar
        showData();
    }

    private void showData() {
        // Mengambil data di dalam bundle
        id_kelas = bundle.getInt(Constant.KEY_ID_KELAS,0);
        nama_wali = bundle.getString(Constant.KEY_NAMA_WALI);
        nama_kelas = bundle.getString(Constant.KEY_NAMA_KELAS);
        Log.i("cek id", String.valueOf(id_kelas));

        // Menampilkan ke layar
        edtNamaKelas.setText(nama_kelas);
        edtNamaWali.setText(nama_wali);
    }

    @OnClick(R.id.btnSimpan)
    public void onViewClicked() {
        // Mengambil data
        getData();

        // Mengirim data ke sqlite
        saveData();

        clearData();

        Toast.makeText(this, "Berhasil di update", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void clearData() {
        edtNamaWali.setText("");
        edtNamaKelas.setText("");
    }

    private void saveData() {
        // Membuat objeck kelas model
        KelasModel kelasModel = new KelasModel();

        // Memasukkan data ke kelas model
        kelasModel.setId_kelas(id_kelas);
        kelasModel.setNama_kelas(nama_kelas);
        kelasModel.setNama_wali(nama_wali);

        // Melakukan operasi update untuk update ke sqlite
        siswaDatabase.kelasDao().update(kelasModel);
    }

    private void getData() {
        // Mengambil inputan user dan dimasukkan ke dalam variable
        nama_kelas = edtNamaKelas.getText().toString();
        nama_wali = edtNamaWali.getText().toString();
    }
}
