package com.adelia.nainggolan.absensisiswa.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.adelia.nainggolan.absensisiswa.API.APIRequestData;
import com.adelia.nainggolan.absensisiswa.API.RetroServer;
import com.adelia.nainggolan.absensisiswa.Activity.MainActivity;
import com.adelia.nainggolan.absensisiswa.Model.DataModel;
import com.adelia.nainggolan.absensisiswa.Model.ResponseModel;
import com.adelia.nainggolan.absensisiswa.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<com.adelia.nainggolan.absensisiswa.Adapter.AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel> listSiswa;
    private int idSiswa;

    public AdapterData(Context ctx, List<DataModel> listSiswa) {
        this.ctx = ctx;
        this.listSiswa = listSiswa;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listSiswa.get(position);

        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvNama.setText(dm.getNama());
        holder.tvKelas.setText(dm.getKelas());
        holder.tvPelajaran.setText(dm.getPelajaran());
        holder.tvKeterangan.setText(dm.getKeterangan());
        holder.tvTanggal.setText(dm.getTanggal());
    }

    @Override
    public int getItemCount() {
        return listSiswa.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
       TextView tvId, tvNama, tvKelas, tvPelajaran, tvKeterangan, tvTanggal;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvKelas = itemView.findViewById(R.id.tv_kelas);
            tvPelajaran = itemView.findViewById(R.id.tv_pelajaran);
            tvKeterangan = itemView.findViewById(R.id.tv_keterangan);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Lanjutkan Operasi");
                    dialogPesan.setCancelable(true);

                    idSiswa =Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteData();
                            dialogInterface.dismiss();
                            ((MainActivity) ctx).retrieveData();
                        }
                    });

                    dialogPesan.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    dialogPesan.show();

                    return false;
                }
            });
        }

        private void deleteData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(idSiswa);

            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : " +t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
