package com.example.shoppingapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBar
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

    //Handle logout in ActionBar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val logoutMenuItem = menu?.findItem(R.id.logoutBtn)
        logoutMenuItem?.setOnMenuItemClickListener {
            firebaseAuth.signOut()
            true
        }
        return true
    }

}