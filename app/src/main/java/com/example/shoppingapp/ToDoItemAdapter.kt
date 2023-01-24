package com.example.shoppingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.TaskItemBinding

class ToDoItemAdapter(val taskList: ArrayList<ToDoItem>, val context: Context) : RecyclerView.Adapter<ToDoItemAdapter.MyHolder>() {
    class MyHolder(val binding: TaskItemBinding) :  RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val task = taskList[position]
        with(holder){
            binding.tvTaskName.text = task.item_name
            binding.chBox.isChecked= task.isChecked
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }


}