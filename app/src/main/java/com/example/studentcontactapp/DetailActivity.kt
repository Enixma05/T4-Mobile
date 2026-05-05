package com.example.studentcontactapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.studentcontactapp.utils.FileHelper

class DetailActivity : AppCompatActivity() {

    private var studentNim: String = ""
    private var studentName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        studentNim = intent.getStringExtra("STUDENT_NIM") ?: ""
        studentName = intent.getStringExtra("STUDENT_NAME") ?: ""
        val studentProdi = intent.getStringExtra("STUDENT_PRODI") ?: ""

        if (studentNim.isEmpty()) {
            Toast.makeText(this, "Data tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val tvBack = findViewById<TextView>(R.id.tvBack)
        val tvAvatar = findViewById<TextView>(R.id.tvAvatar)
        val tvDetailName = findViewById<TextView>(R.id.tvDetailName)
        val tvDetailInfo = findViewById<TextView>(R.id.tvDetailInfo)
        val etNote = findViewById<EditText>(R.id.etNote)
        val btnSaveNote = findViewById<Button>(R.id.btnSaveNote)
        val btnLoadNote = findViewById<Button>(R.id.btnLoadNote)
        val tvNoteStatus = findViewById<TextView>(R.id.tvNoteStatus)

        tvDetailName.text = studentName
        tvDetailInfo.text = "$studentNim · $studentProdi"

        if (studentName.isNotEmpty()) {
            val initials = studentName.split(" ")
                .mapNotNull { it.firstOrNull() }
                .take(2)
                .joinToString("")
                .uppercase()
            tvAvatar.text = initials
        }

        tvBack.setOnClickListener {
            finish()
        }

        fun updateStatus() {
            try {
                if (FileHelper.isNoteExists(this, studentNim)) {
                    val size = FileHelper.getFileSize(this, studentNim)
                    tvNoteStatus.text = "✓ Tersimpan ($size bytes)"
                } else {
                    tvNoteStatus.text = "Status: Belum ada catatan"
                }
            } catch (e: Exception) {
                tvNoteStatus.text = "Error membaca file"
            }
        }

        try {
            val note = FileHelper.loadNote(this, studentNim)
            etNote.setText(note ?: "")
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal load catatan", Toast.LENGTH_SHORT).show()
        }

        updateStatus()

        btnSaveNote.setOnClickListener {
            val content = etNote.text.toString()
            FileHelper.saveNote(this, studentNim, content)
            Toast.makeText(this, "Catatan disimpan", Toast.LENGTH_SHORT).show()
            updateStatus()
        }

        btnLoadNote.setOnClickListener {
            val note = FileHelper.loadNote(this, studentNim)
            if (note != null) {
                etNote.setText(note)
                Toast.makeText(this, "Catatan dimuat", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Belum ada catatan", Toast.LENGTH_SHORT).show()
            }
            updateStatus()
        }
    }
}