package com.example.shoppingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.TaskItemBinding

class ToDoItemAdapter(private val taskList: ArrayList<ToDoItem>, private val context: Context) :
    RecyclerView.Adapter<ToDoItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]

        holder.chBox.isChecked = task.isChecked
        holder.tvTaskName.text = task.item_name
        holder.btnRemove.setOnClickListener {
            (context as HomeActivity).onTaskDelete(task)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chBox: CheckBox = itemView.findViewById(R.id.chBox)
        val tvTaskName: TextView = itemView.findViewById(R.id.tvTaskName)
        val btnRemove: ImageButton = itemView.findViewById(R.id.btnRemove)
    }
}
