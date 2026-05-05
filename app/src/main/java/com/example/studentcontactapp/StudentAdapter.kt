package com.example.studentcontactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.database.StudentEntity

class StudentAdapter(
    private var list: List<StudentEntity>,
    private val onClick: (StudentEntity) -> Unit,
    private val onEdit: (StudentEntity) -> Unit,
    private val onDelete: (StudentEntity) -> Unit
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvName: TextView = v.findViewById(R.id.tvStudentName)
        val tvNim: TextView = v.findViewById(R.id.tvStudentNim)
        val btnEdit: TextView = v.findViewById(R.id.btnEdit)
        val btnDelete: TextView = v.findViewById(R.id.btnDelete)
        val tvAvatar: TextView = v.findViewById(R.id.tvAvatarCircle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvName.text = item.name
        holder.tvNim.text = item.nim

        if (item.name.isNotEmpty()) {
            val initials = item.name.split(" ")
                .mapNotNull { it.firstOrNull() }
                .take(2)
                .joinToString("")
                .uppercase()
            holder.tvAvatar.text = initials
        } else {
            holder.tvAvatar.text = ""
        }

        holder.itemView.setOnClickListener {
            onClick(item)
        }

        holder.btnEdit.setOnClickListener {
            onEdit(item)
        }

        holder.btnDelete.setOnClickListener {
            onDelete(item)
        }
    }

    fun updateData(newList: List<StudentEntity>) {
        list = newList
        notifyDataSetChanged()
    }

    fun getStudentAt(position: Int): StudentEntity {
        return list[position]
    }
}