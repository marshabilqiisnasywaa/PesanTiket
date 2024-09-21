package com.example.pesantiket

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pesantiket.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UI components
        setupDateAndTimePickers()
        setupSpinner()
        setupTicketButton()
    }

    private fun setupDateAndTimePickers() {
        // Set click listener for departure date
        binding.tanggalKeberangkatan.setOnClickListener {
            showDatePicker(binding.tanggalKeberangkatan)
        }

        // Set click listener for departure time
        binding.jamKeberangkatan.setOnClickListener {
            showTimePicker(binding.jamKeberangkatan)
        }
    }

    private fun setupSpinner() {
        // Populate destination spinner with options
        val tujuanList = arrayOf("Pilih Tujuan", "Jakarta", "Bandung", "Yogyakarta", "Surabaya")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, tujuanList) // Use custom layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tujuanSpinner.adapter = adapter
    }


    private fun setupTicketButton() {
        // Set up book ticket button
        binding.pesanTiketBtn.setOnClickListener {
            val nama = binding.namaPemesan.text.toString().trim()
            val tujuan = binding.tujuanSpinner.selectedItem.toString()
            val jam = binding.jamKeberangkatan.text.toString().trim()
            val tanggal = binding.tanggalKeberangkatan.text.toString().trim()

            // Validate input
            if (nama.isEmpty() || jam.isEmpty() || tanggal.isEmpty() || tujuan == "Pilih Tujuan") {
                Toast.makeText(this, "Silakan lengkapi semua informasi dan pilih tujuan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show confirmation dialog
            showConfirmationDialog(nama, tujuan, jam, tanggal)
        }
    }

    private fun showConfirmationDialog(nama: String, tujuan: String, jam: String, tanggal: String) {
        val dialogView = layoutInflater.inflate(R.layout.alert_pesan_tiket, null)
        val dialogBuilder = android.app.AlertDialog.Builder(this)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        // Set ticket price (this can be modified based on the selected destination)
        val ticketPriceTextView = dialogView.findViewById<TextView>(R.id.ticketPrice)
        ticketPriceTextView.text = "Rp200.000"

        // Set button actions
        dialogView.findViewById<Button>(R.id.cancelButton).setOnClickListener {
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.buyButton).setOnClickListener {
            // Proceed to ConfirmationActivity
            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra("NAMA", nama)
                putExtra("JAM", jam)
                putExtra("TANGGAL", tanggal)
                putExtra("TUJUAN", tujuan)
            }
            startActivity(intent)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun showDatePicker(tanggalKeberangkatan: EditText) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                tanggalKeberangkatan.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePicker(jamKeberangkatan: EditText) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                jamKeberangkatan.setText(String.format("%02d:%02d", hourOfDay, minute))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).apply {
            setTitle("Setel Waktu") // Set the title of the TimePickerDialog
        }
        timePickerDialog.show()
    }
}
