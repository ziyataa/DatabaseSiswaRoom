package com.ziyata.databasesiswa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ziyata.databasesiswa.R;
import com.ziyata.databasesiswa.db.Constant;
import com.ziyata.databasesiswa.db.SiswaDatabase;
import com.ziyata.databasesiswa.model.SiswaModel;
import com.ziyata.databasesiswa.ui.UpdateSiswaActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.ViewHolder> {

    private Context context;
    private List<SiswaModel> siswaModelList;

    public SiswaAdapter(Context context, List<SiswaModel> siswaModelList) {
        this.context = context;
        this.siswaModelList = siswaModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_siswa, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        // Memindahkan data yang dipilih ke dalam list
        final SiswaModel siswaModel = siswaModelList.get(i);

        // Kita tampilkan data ke layar
        viewHolder.txtNameSiswa.setText(siswaModel.getNama());

        // Mengambil huruf pertama
        String nama = siswaModel.getNama().substring(0, 1);

        // Membuat color generator untuk mendapatkan color material
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();

        // Mensetting TextDrawable untuk membuat lingkaran
        TextDrawable drawable = TextDrawable.builder().buildRound(nama, color);

        // Tampilkan gambar lingkaran ke layar
        viewHolder.imgView.setImageDrawable(drawable);

        // Membuat siswadatabase
        final SiswaDatabase siswaDatabase = SiswaDatabase.createDatbase(context);

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure delete " + siswaModel.getNama() + "?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        siswaDatabase.kelasDao().deleteSiswa(siswaModel);
                        siswaModelList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(0, siswaModelList.size());

                        Toast.makeText(context, "Berhasi dihapus", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // Membuat variable alertDialog
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                // Menampilkan alertDialog
                alertDialog.show();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.id_siswa, siswaModel.getId_siswa());
                bundle.putString(Constant.nama_siswa, siswaModel.getNama());
                bundle.putString(Constant.umur, siswaModel.getUmur());
                bundle.putString(Constant.jenis_kelamin, siswaModel.getJenis_kelamin());
                bundle.putString(Constant.asal, siswaModel.getAsal());
                bundle.putString(Constant.email, siswaModel.getEmail());
                bundle.putInt(Constant.id_kelas, siswaModel.getId_kelas());

                context.startActivity(new Intent(context, UpdateSiswaActivity.class).putExtras(bundle));
            }
        });

    }

    @Override
    public int getItemCount() {
        return siswaModelList.size();
    }

    @OnClick(R.id.btnDelete)
    public void onViewClicked() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgView)
        ImageView imgView;
        @BindView(R.id.txt_name_siswa)
        TextView txtNameSiswa;
        @BindView(R.id.btnDelete)
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
