package com.example.shoppingapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference


class ToDoAdapter(val items :MutableList<ToDoItem?>, val context: Context, val ref: DatabaseReference) : RecyclerView.Adapter<ViewHolder>() {

    // Binds each animal in the ArrayList to a view
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val item: ToDoItem ?= items.get(position)
        if(item!=null) {
            holder.checkboxItem.text = item.level
            holder.checkboxItem.isChecked = item.status

            holder.checkboxItem.setOnCheckedChangeListener { _, _ ->

                item.statusChanged()
                //update status on firebase
                ref.child(item.id).setValue(item)
            }

            holder.buttonDelete.setOnClickListener {
                ref.child(item.id).removeValue()
                items.remove(item)
                notifyDataSetChanged()
            }
        }
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_list_item, parent, false))
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val checkboxItem: CheckBox = view.findViewById(R.id.checkBox_item)
    val buttonDelete: ImageView = view.findViewById(R.id.button_task_delete)
}
