package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.StudentEntity
import com.example.studentcontactapp.utils.PrefManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var adapter: StudentAdapter
    private val list = mutableListOf<StudentEntity>()
    private lateinit var dao: com.example.studentcontactapp.database.StudentDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvStudents)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        val tvWelcomeHome = view.findViewById<TextView>(R.id.tvWelcomeHome)

        dao = AppDatabase.getInstance(requireContext()).studentDao()

        val prefManager = PrefManager(requireContext())
        tvWelcomeHome.text = "Welcome, ${prefManager.getUsername()}!"

        adapter = StudentAdapter(
            list,
            onClick = { student ->
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("STUDENT_NIM", student.nim)
                intent.putExtra("STUDENT_NAME", student.name)
                intent.putExtra("STUDENT_PRODI", student.prodi)
                startActivity(intent)
            },
            onEdit = { student ->
                val intent = Intent(requireContext(), StudentFormActivity::class.java)
                intent.putExtra("STUDENT_ID", student.id)
                startActivity(intent)
            },
            onDelete = { student ->
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                val student = adapter.getStudentAt(position)

                showDeleteDialog(student)
            }
        }

        ItemTouchHelper(swipeHandler).attachToRecyclerView(recyclerView)

        fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), StudentFormActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudentData()
    }

    private fun loadStudentData() {
        viewLifecycleOwner.lifecycleScope.launch {

            val data = dao.getAllStudents()

            if (data.isEmpty()) {
                val sampleData = listOf(
                    StudentEntity(
                        name = "Budi",
                        nim = "001",
                        prodi = "TI",
                        email = "budi@mail.com",
                        semester = 3
                    ),
                    StudentEntity(
                        name = "Ani",
                        nim = "002",
                        prodi = "SI",
                        email = "ani@mail.com",
                        semester = 5
                    )
                )

                dao.insertAll(sampleData)

                loadStudentData()
            } else {
                list.clear()
                list.addAll(data)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun showDeleteDialog(student: StudentEntity) {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Hapus Data?")
        builder.setMessage("Hapus \"${student.name}\"? Tindakan ini tidak dapat dibatalkan.")

        builder.setPositiveButton("Hapus") { _, _ ->
            viewLifecycleOwner.lifecycleScope.launch {
                dao.deleteById(student.id)
                loadStudentData()
                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            loadStudentData()
            dialog.dismiss()
        }

        builder.setOnCancelListener {
            loadStudentData()
        }

        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(android.graphics.Color.RED)

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(android.graphics.Color.GRAY)
    }
}