package com.ziyata.databasesiswa.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ziyata.databasesiswa.R;
import com.ziyata.databasesiswa.db.Constant;
import com.ziyata.databasesiswa.db.SiswaDatabase;
import com.ziyata.databasesiswa.model.SiswaModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahSiswaActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaSiswa)
    EditText edtNamaSiswa;
    @BindView(R.id.edtUmur)
    EditText edtUmur;
    @BindView(R.id.radio_laki)
    RadioButton radioLaki;
    @BindView(R.id.radio_perempuan)
    RadioButton radioPerempuan;
    @BindView(R.id.edtAsal)
    EditText edtAsal;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.btnSave)
    Button btn;
    @BindView(R.id.radioJenisKelamin)
    RadioGroup radioJenisKelamin;

    // TODO 1 membuat variable yg dibutuhkan
    private SiswaDatabase siswaDatabase;
    private int id_kelas;
    private String namaSiswa, asal, umur, jenis_kelamin, email;
    private boolean empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_siswa);
        ButterKnife.bind(this);

        // TODO 2 mensetting dan menangkap data dari activity sebelumnya
        // Mensetting title
        setTitle("Add Siswa");

        // Menangkap id_kelas dari activity sebelumnya
        id_kelas = getIntent().getIntExtra(Constant.KEY_ID_KELAS, 0);

        // Kita buat object database
        siswaDatabase = SiswaDatabase.createDatbase(this);

    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        // Memastikan semuanya terisi
        cekData();

        if (!empty) {
            saveData();
            clearData();
            Toast.makeText(this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Masih ada kolom yang kosong", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearData() {
        edtNamaSiswa.setText("");
        edtUmur.setText("");
        edtAsal.setText("");
        edtEmail.setText("");
        radioJenisKelamin.clearCheck();

    }

    private void saveData() {
        // Membuat penampung dengan membuat object SiswaModel
        SiswaModel siswaModel = new SiswaModel();

        // Kita masukkan data ke dalam siswaModel
        siswaModel.setId_kelas(id_kelas);
        siswaModel.setNama(namaSiswa);
        siswaModel.setAsal(asal);
        siswaModel.setUmur(umur);
        siswaModel.setJenis_kelamin(jenis_kelamin);
        siswaModel.setEmail(email);

        // Kita lakukan operasi insert
        siswaDatabase.kelasDao().insertSiswa(siswaModel);
    }

    private void cekData() {
        namaSiswa = edtNamaSiswa.getText().toString();
        asal = edtAsal.getText().toString();
        email = edtEmail.getText().toString();
        umur = edtUmur.getText().toString();

        empty = namaSiswa.isEmpty() || asal.isEmpty() || umur.isEmpty() || email.isEmpty() || jenis_kelamin.isEmpty();
    }

    @OnClick({R.id.radio_laki, R.id.radio_perempuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_laki:
                jenis_kelamin = radioLaki.getText().toString();
                break;
            case R.id.radio_perempuan:
                jenis_kelamin = radioPerempuan.getText().toString();
                break;
        }
    }
}
