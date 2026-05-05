package com.example.studentcontactapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.StudentEntity
import kotlinx.coroutines.launch

class StudentFormActivity : AppCompatActivity() {

    private var studentId: Int = 0
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_form)

        val dao = AppDatabase.getInstance(this).studentDao()

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val etName = findViewById<EditText>(R.id.etName)
        val etNim = findViewById<EditText>(R.id.etNim)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etSemester = findViewById<EditText>(R.id.etSemester)
        val spProdi = findViewById<Spinner>(R.id.spProdi)
        val btnSave = findViewById<Button>(R.id.btnSave)

        tvTitle.setOnClickListener {
            finish()
        }

        studentId = intent.getIntExtra("STUDENT_ID", 0)
        if (studentId != 0) {
            isEditMode = true
            tvTitle.text = "Edit Data Mahasiswa"
            btnSave.text = "Update Data"

            lifecycleScope.launch {
                val student = dao.getStudentById(studentId)
                student?.let {
                    etName.setText(it.name)
                    etNim.setText(it.nim)
                    etEmail.setText(it.email)
                    etSemester.setText(it.semester.toString())

                    val prodiArray = resources.getStringArray(R.array.prodi_array)
                    val index = prodiArray.indexOf(it.prodi)
                    if (index >= 0) spProdi.setSelection(index)
                }
            }
        }

        btnSave.setOnClickListener {

            val name = etName.text.toString().trim()
            val nim = etNim.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val semesterText = etSemester.text.toString().trim()
            val prodi = spProdi.selectedItem.toString()

            if (name.isEmpty() || nim.isEmpty() || semesterText.isEmpty()) {
                Toast.makeText(
                    this,
                    "Nama, NIM, dan Semester tidak boleh kosong!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val semester = semesterText.toIntOrNull()
            if (semester == null) {
                Toast.makeText(this, "Harap isi semester dengan angka!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (isEditMode) {
                    val student = StudentEntity(
                        id = studentId,
                        name = name,
                        nim = nim,
                        prodi = prodi,
                        email = email,
                        semester = semester
                    )
                    dao.update(student)

                    Toast.makeText(
                        this@StudentFormActivity,
                        "Data berhasil diperbarui!",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    val student = StudentEntity(
                        name = name,
                        nim = nim,
                        prodi = prodi,
                        email = email,
                        semester = semester
                    )
                    dao.insert(student)

                    Toast.makeText(
                        this@StudentFormActivity,
                        "Data berhasil disimpan!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                finish()
            }
        }
    }
}