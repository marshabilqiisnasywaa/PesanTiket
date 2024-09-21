package com.example.pesantiket

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_confirmation)

        // Ambil data dari Intent yang dikirim dari MainActivity
        val nama = intent.getStringExtra("NAMA") ?: "Tidak Diketahui"
        val jam = intent.getStringExtra("JAM") ?: "Tidak Diketahui"
        val tanggal = intent.getStringExtra("TANGGAL") ?: "Tidak Diketahui"
        val tujuan = intent.getStringExtra("TUJUAN") ?: "Tidak Diketahui"

        // Set data ke TextView
        findViewById<TextView>(R.id.namaText).text = "Nama   : $nama"
        findViewById<TextView>(R.id.jamText).text = "Jam    : $jam"
        findViewById<TextView>(R.id.tanggalText).text = "Tanggal: $tanggal"
        findViewById<TextView>(R.id.tujuanText).text = "Tujuan : $tujuan"
    }
}
