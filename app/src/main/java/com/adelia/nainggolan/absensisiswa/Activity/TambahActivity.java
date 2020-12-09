package com.adelia.nainggolan.absensisiswa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adelia.nainggolan.absensisiswa.API.APIRequestData;
import com.adelia.nainggolan.absensisiswa.API.RetroServer;
import com.adelia.nainggolan.absensisiswa.Model.ResponseModel;
import com.adelia.nainggolan.absensisiswa.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etKelas, etPelajaran, etKeterangan, etTanggal;
    private Button btnSimpan;
    private String nama, kelas, pelajaran, keterangan, tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etKelas = findViewById(R.id.et_kelas);
        etPelajaran = findViewById(R.id.et_pelajaran);
        etKeterangan = findViewById(R.id.et_keterangan);
        etTanggal = findViewById(R.id.et_tanggal);

        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = etNama.getText().toString();
                kelas = etKelas.getText().toString();
                pelajaran = etPelajaran.getText().toString();
                keterangan = etKeterangan.getText().toString();
                tanggal = etTanggal.getText().toString();

                if (nama.trim().equals("")){
                    etNama.setError("Nama Harus Diisi");
                }
                else if (kelas.trim().equals("")){
                    etKelas.setError("Kelas Harus Diisi");
                }
                else if (pelajaran.trim().equals("")){
                    etPelajaran.setError("Pelajaran Harus Diisi");
                }
                else if (keterangan.trim().equals("")){
                    etKeterangan.setError("Keterangan Hadir Harus Diisi");
                }
                else if (tanggal.trim().equals("")){
                    etTanggal.setError("Tanggal Harus Diisi");
                }
                else {
                    createData();
                }
            }
        });
    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateData(nama, kelas, pelajaran, keterangan, tanggal);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }


            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}