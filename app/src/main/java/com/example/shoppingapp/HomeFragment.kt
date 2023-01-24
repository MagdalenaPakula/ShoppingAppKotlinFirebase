//package com.example.shoppingapp
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.appcompat.app.ActionBar
//import com.example.shoppingapp.databinding.FragmentHomeBinding
//import com.google.android.material.textfield.TextInputEditText
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//class HomeFragment : Fragment(), AddTodoPopupFragment.DialogNextBtnClickListener {
//    //viewBinding
//    private lateinit var binding: FragmentHomeBinding
//
//    //ActionBar
//    private lateinit var actionBar: ActionBar
//
//    //Firebase auth
//    private lateinit var firebaseAuth: FirebaseAuth
//
//    //Database
//    private lateinit var databaseRef: DatabaseReference
//
//    //AddPopUp
//    private lateinit var popUpFragment: AddTodoPopupFragment
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return inflater.inflate(R.layout.fragment_home, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        init(view)
//    }
//
//    private fun init(view: View){
//        //configure actionbar
//        //requireActivity().supportActionBar?.title = "What's on Your mind today?"
////        actionBar = supportActionBar!!
////        actionBar.title = "What's on Your mind today?"
//
//        //init firebase auth
//        firebaseAuth = FirebaseAuth.getInstance()
//        checkUser()
//
//        //init firebase database
//        databaseRef = FirebaseDatabase.getInstance().reference
//            .child("Tasks").child(firebaseAuth.currentUser?.uid.toString())
//
//        //handle logout
//        binding.logoutBtn.setOnClickListener {
//            firebaseAuth.signOut()
//            checkUser()
//        }
//        //Adding
//        registerEvents()
//    }
//    private fun checkUser() {
//        //check if user is logged in
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            //ok
//            val email = firebaseUser.email
//        } else {
//            //not ok
//            startActivity(Intent(requireContext(), LoginActivity::class.java))
//            activity?.finish()
//        }
//    }
//
//    private fun registerEvents(){
//        binding.addBtnHome.setOnClickListener{
//            popUpFragment = AddTodoPopupFragment()
//            popUpFragment.setListener(this)
//            popUpFragment.show(childFragmentManager, "AddTodoPopupFragment")
//
//        }
//    }
//
//    override fun onSaveTask(todo: String, todoEt: TextInputEditText) {
//        databaseRef.push().setValue(todo).addOnCompleteListener{
//            if(it.isSuccessful){
//                Toast.makeText(context,"Todo saved successfully", Toast.LENGTH_SHORT).show()
//                todoEt.text = null
//
//            }else{
//                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
//            }
//            popUpFragment.dismiss()
//
//        }
//    }
//
//}