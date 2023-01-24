package com.example.shoppingapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity(), View.OnClickListener  {

    //viewBinding
    private lateinit var binding: ActivityHomeBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //Firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //Database
    //private lateinit var database: FirebaseDatabase
    //init firebase database
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("todo-list")
    var todos: ArrayList<ToDoItem?> = ArrayList()
    val adapter = ToDoAdapter(todos, this, myRef)

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
        checkUser()

//        //init firebase database
//        database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("todo-list")
//        var todos: ArrayList<ToDoItem?> = ArrayList()
//        val adapter = ToDoAdapter(todos, this, myRef)

        //Adding
        findViewById<Button>(R.id.button_add_todo).setOnClickListener(this)

        // initialise recycler view
        findViewById<RecyclerView>(R.id.recyclerView_todo_list).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recyclerView_todo_list).adapter = adapter
    }

    //Handle logout in ActionBar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val logoutMenuItem = menu?.findItem(R.id.logoutBtn)
        logoutMenuItem?.setOnMenuItemClickListener {
            firebaseAuth.signOut()
            checkUser()
            true
        }
        return true
    }

    //Check if user is logged in
    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //ok
            //val email = firebaseUser.email
        } else {
            //not ok
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(view: View?) {

        when (view?.id) {
            // add item button action
            R.id.button_add_todo -> {
                val level = findViewById<EditText>(R.id.editText_todo_item).text.toString()
                if (level.isNotEmpty()) {

                    val id: String = myRef.push().key.toString()

                    val todoItem = ToDoItem(id, level)

                    findViewById<EditText>(R.id.editText_todo_item).setText("")

                    //save data on firebase
                    myRef.child(id).setValue(todoItem)

                    todos.add(todoItem)
                    adapter.notifyDataSetChanged()

                    Toast.makeText(
                        applicationContext,
                        "Item Added successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}