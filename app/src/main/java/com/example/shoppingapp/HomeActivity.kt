package com.example.shoppingapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity()  {

    //viewBinding
    private lateinit var binding: ActivityHomeBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //Firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //Database
    private lateinit var database: FirebaseFirestore


    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "What's on Your mind today?"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //init firebase database
        database= FirebaseFirestore.getInstance()

        //currentUser
        var currentUser = firebaseAuth.currentUser

        loadAllData(currentUser!!.uid.toString())

        //Adding
        binding.buttonAddTodo.setOnClickListener{
            var task = binding.editTextTodoItem.text.toString().trim()

            if (task.isEmpty()){
                return@setOnClickListener binding.editTextTodoItem.setError("Item cannot be empty")
            }

            val taskData = ToDoItem(task, false, currentUser!!.uid)
            val data = hashMapOf(
                "item_name" to taskData.item_name,
                "isChecked" to taskData.isChecked,
                "UID" to taskData.UID
            )
            database.collection("all_items").add(data)
                .addOnSuccessListener {
                Toast.makeText(this@HomeActivity, "Task Saved", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(this@HomeActivity, "Task NOT Saved", Toast.LENGTH_SHORT).show()
                    Log.e("HA", "Error saving: Err:"+ it.message)

                }
        }


    }

    private fun loadAllData(UID: String) {

        val taskList = ArrayList<ToDoItem>()
        var ref = database.collection("all_items")
        ref.whereEqualTo("UID", UID)
            .get().
            addOnSuccessListener {
            if(it.isEmpty){
                Toast.makeText(this@HomeActivity, "No task found", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }
            for(doc in it){
                val todoitem = doc.toObject(ToDoItem::class.java)
                taskList.add(todoitem)
            }
            binding.recyclerViewTodoList.apply {
                layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
                adapter = ToDoItemAdapter(taskList, this@HomeActivity)
            }
        }

    }

    //Handle logout in ActionBar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val logoutMenuItem = menu?.findItem(R.id.logoutBtn)
        logoutMenuItem?.setOnMenuItemClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
        return true
    }

    fun onTaskDelete(task:ToDoItem){
        database.collection("all_items").document(task.documentId).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error deleting task", Toast.LENGTH_SHORT).show()
                Log.e("HA", "Error deleting task: ${it.message}")
            }
    }
    }
