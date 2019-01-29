package com.ziyata.databasesiswa.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ziyata.databasesiswa.R;
import com.ziyata.databasesiswa.db.Constant;
import com.ziyata.databasesiswa.db.SiswaDatabase;
import com.ziyata.databasesiswa.model.KelasModel;
import com.ziyata.databasesiswa.ui.MainSiswa;
import com.ziyata.databasesiswa.ui.UpdateKelasActivity;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {

    //Membuat variable untuk menampung context
    private final Context context;

    // Membuat variable list dengan cetakan KelasModel
    private final List<KelasModel> kelasModelList;

    // membuat variable database
    private SiswaDatabase siswaDatabase;

    // Membuat variable bundle untuk membawa data
    Bundle bundle;

    public KelasAdapter(Context context, List<KelasModel> kelasModelList) {
        this.context = context;
        this.kelasModelList = kelasModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_kelas, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        // Memindahkan data di dalam list dengan index position ke dalam KelasModel
        final KelasModel kelasModel = kelasModelList.get(i);

        // Menampilkan data ke layar
        viewHolder.tvWali.setText(kelasModel.getNama_wali());
        viewHolder.tvKelas.setText(kelasModel.getNama_kelas());

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        // Mensetting color background card view
        viewHolder.cvKelas.setCardBackgroundColor(color);

        // Membuat onClick icon overflow
        viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Buat object database
                siswaDatabase = siswaDatabase.createDatbase(context);

                // Membuat object PopupMenu
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);

                // Inflate menu ke dalam popum menu
                popupMenu.inflate(R.menu.popup_menu);

                //OnClick pada salah satu menu pada popup menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                // Membuat object alert dialog
                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
                                // Mengisi / nyeting alertDialog
                                alertDialogBuilder.setMessage("Are you sure delete " + kelasModel.getNama_kelas() + " ?");
                                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // Menghapus operasi delete data
                                        siswaDatabase.kelasDao().delete(kelasModel);

                                        // Menghapus data yang telah di hapus pada list
                                        kelasModelList.remove(i);

                                        // Memberitahu bahwa data sudah hilang
                                        notifyItemRemoved(i);
                                        notifyItemRangeChanged(0, kelasModelList.size());

                                        Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();

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


                                break;
                            case R.id.edit:
                                // Membuat object bundle
                                bundle = new Bundle();

                                // Mengisi bundle dengan data
                                bundle.putInt(Constant.KEY_ID_KELAS, kelasModel.getId_kelas());
                                bundle.putString(Constant.KEY_NAMA_KELAS, kelasModel.getNama_kelas());
                                bundle.putString(Constant.KEY_NAMA_WALI, kelasModel.getNama_wali());

                                // Berpindah halaman dengan membawa data
                                context.startActivity(new Intent(context, UpdateKelasActivity.class).putExtras(bundle));

                                break;
                        }
                        return true;
                    }
                });
                // Menampilkan menu
                popupMenu.show();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putInt(Constant.KEY_ID_KELAS, kelasModel.getId_kelas());
                bundle.putString(Constant.KEY_NAMA_KELAS, kelasModel.getNama_kelas());
                context.startActivity(new Intent(context, MainSiswa.class).putExtras(bundle));
            }
        });


    }

    @Override
    public int getItemCount() {
        return kelasModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_kelas)
        TextView tvKelas;
        @BindView(R.id.tv_wali)
        TextView tvWali;
        @BindView(R.id.cv_kelas)
        CardView cvKelas;
        @BindView(R.id.overflow)
        ImageButton overflow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
