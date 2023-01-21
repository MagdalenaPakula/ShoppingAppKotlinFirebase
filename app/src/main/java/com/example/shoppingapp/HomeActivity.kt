package com.example.shoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.shoppingapp.databinding.ActivityHomeBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity(), AddTodoPopupFragment.DialogNextBtnClickListener {

    //viewBinding
    private lateinit var binding: ActivityHomeBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //Firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //Database
    private lateinit var databaseRef: DatabaseReference

    //AddPopUp
    private lateinit var popUpFragment: AddTodoPopupFragment


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

        //init firebase database
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("Tasks").child(firebaseAuth.currentUser?.uid.toString())

        //handle logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
        //Adding
        registerEvents()
    }
    private fun checkUser() {
        //check if user is logged in
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
    private fun registerEvents(){
        binding.addBtnHome.setOnClickListener{
            popUpFragment = AddTodoPopupFragment()
            popUpFragment.setListener(this)
            popUpFragment.show(supportFragmentManager, "AddTodoPopupFragment")

        }
    }

    override fun onSaveTask(todo: String, todoEt: TextInputEditText) {
        databaseRef.push().setValue(todo).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(applicationContext,"Todo saved successfully", Toast.LENGTH_SHORT).show()
                todoEt.text = null

            }else{
                Toast.makeText(applicationContext, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            popUpFragment.dismiss()

        }
    }
}