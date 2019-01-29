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

public class UpdateSiswaActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaSiswa)
    EditText edtNamaSiswa;
    @BindView(R.id.edtUmur)
    EditText edtUmur;
    @BindView(R.id.radio_laki)
    RadioButton radioLaki;
    @BindView(R.id.radio_perempuan)
    RadioButton radioPerempuan;
    @BindView(R.id.radioJenisKelamin)
    RadioGroup radioJenisKelamin;
    @BindView(R.id.edtAsal)
    EditText edtAsal;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;


    private Bundle bundle;
    private SiswaDatabase siswaDatabase;
    int id_siswa;
    int id_kelas;
    String nama_siswa, umur, jenis_kelamin, asal, email;
    private boolean empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_siswa);
        ButterKnife.bind(this);

        setTitle("Update data siswa");
        bundle = getIntent().getExtras();

        siswaDatabase = SiswaDatabase.createDatbase(this);

        id_kelas = bundle.getInt(Constant.id_kelas);
        id_siswa = bundle.getInt(Constant.id_siswa);

        showData();
    }

    private void showData() {
        nama_siswa = bundle.getString(Constant.nama_siswa);
        umur = bundle.getString(Constant.umur);
        asal = bundle.getString(Constant.asal);
        email = bundle.getString(Constant.email);
        jenis_kelamin = bundle.getString(Constant.jenis_kelamin);


        if (jenis_kelamin.equals(radioLaki.getText().toString())) {
            radioJenisKelamin.check(radioLaki.getId());
        }
        if (jenis_kelamin.equals(radioPerempuan.getText().toString())) {
            radioJenisKelamin.check(radioPerempuan.getId());
        }


        edtNamaSiswa.setText(nama_siswa);
        edtUmur.setText(umur);
        edtAsal.setText(asal);
        edtEmail.setText(email);
    }


    private void getData() {
        nama_siswa = edtNamaSiswa.getText().toString();
        umur = edtUmur.getText().toString();
        asal = edtAsal.getText().toString();
        email = edtEmail.getText().toString();

        empty = nama_siswa.isEmpty() || umur.isEmpty() || jenis_kelamin.isEmpty() || asal.isEmpty() || email.isEmpty() || jenis_kelamin.isEmpty();

        if (!empty) {
            updateData();
            clearData();

            Toast.makeText(this, "Berhasil di update", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearData() {
        edtNamaSiswa.setText("");
        edtEmail.setText("");
        edtAsal.setText("");
        edtUmur.setText("");
        radioJenisKelamin.clearCheck();

    }

    private void updateData() {
        SiswaModel siswaModel = new SiswaModel();
        siswaModel.setNama(nama_siswa);
        siswaModel.setUmur(umur);
        siswaModel.setJenis_kelamin(jenis_kelamin);
        siswaModel.setAsal(asal);
        siswaModel.setEmail(email);
        siswaModel.setId_siswa(id_siswa);
        siswaModel.setId_kelas(id_kelas);

        siswaDatabase.kelasDao().updateSiswa(siswaModel);
    }

    @OnClick({R.id.radio_laki, R.id.radio_perempuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_laki:
                break;
            case R.id.radio_perempuan:
                break;
        }
    }

    @OnClick(R.id.btnUpdate)
    public void onViewClicked() {
        getData();
    }
}
