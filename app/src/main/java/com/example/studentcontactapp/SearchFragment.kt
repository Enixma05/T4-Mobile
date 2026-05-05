package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import androidx.core.widget.addTextChangedListener
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.StudentEntity
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val list = mutableListOf<StudentEntity>()
    private lateinit var adapter: StudentAdapter
    private lateinit var dao: com.example.studentcontactapp.database.StudentDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = AppDatabase.getInstance(requireContext()).studentDao()

        val rv = view.findViewById<RecyclerView>(R.id.rvSearchResults)
        val etSearch = view.findViewById<EditText>(R.id.etSearch)

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
            onDelete = {
            }
        )

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        etSearch.addTextChangedListener {
            val keyword = it.toString()
            searchData(keyword)
        }

        searchData("")
    }

    private fun searchData(keyword: String) {
        viewLifecycleOwner.lifecycleScope.launch {

            val data = if (keyword.isEmpty()) {
                dao.getAllStudents()
            } else {
                dao.searchStudents(keyword)
            }

            list.clear()
            list.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }
}