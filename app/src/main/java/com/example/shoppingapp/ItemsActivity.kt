//package com.example.shoppingapp
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.ListView
//import android.widget.Toast
//import androidx.appcompat.app.ActionBar
//import androidx.appcompat.app.AlertDialog
//import com.example.shoppingapp.databinding.ActivityItemsBinding
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.*
//
//class ItemsActivity : AppCompatActivity(), UpdateAndDelete {
//    //Binding
//    private lateinit var binding: ActivityItemsBinding
//
//    //ActionBar
//    private lateinit var actionBar: ActionBar
//
//    //Firebase auth
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var database:DatabaseReference
//    var itemSList: MutableList<ItemsModel>? = null
//    lateinit var adapter: ItemsAdapter
//    private var listViewItem: ListView? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityItemsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        //setContentView(R.layout.activity_items)
//        val fab = findViewById<View>(R.id.fab) as ImageView
//        listViewItem = findViewById(R.id.item_listView)
//
//        database = FirebaseDatabase.getInstance().reference
//
//        fab.setOnClickListener{
//            val alertDialog = AlertDialog.Builder(this)
//            val textEditText = EditText(this)
//            alertDialog.setMessage("Add grocery item")
//            alertDialog.setMessage("Enter grocery item")
//            alertDialog.setView(textEditText)
//            alertDialog.setPositiveButton("Add"){dialog, _ ->
//                val shoppingItemData = ItemsModel.createList()
//                shoppingItemData.itemDataText = textEditText.text.toString()
//                shoppingItemData.done = false
//
//                val newItemData = database.child("shopping").push()
//                shoppingItemData.UID = newItemData.key
//
//                newItemData.setValue(shoppingItemData)
//
//                dialog.dismiss()
//                Toast.makeText(this, "item saved", Toast.LENGTH_SHORT).show()
//            }
//            alertDialog.show()
//        }
//
//        itemSList = mutableListOf()
//        adapter = ItemsAdapter(this, itemSList!!)
//        listViewItem!!.adapter = adapter
//        database.addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                itemSList!!.clear()
//                addItemToList(snapshot)
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(applicationContext, "No item added", Toast.LENGTH_LONG).show()
//            }
//        })
//        firebaseAuth = FirebaseAuth.getInstance()
//        checkUser()
//
//        //handle logout
//        binding.logoutBtn.setOnClickListener {
//            firebaseAuth.signOut()
//            checkUser()
//        }
//    }
//    private fun checkUser() {
//        //check if user is logged in
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            //ok
//            val email = firebaseUser.email
//            binding.emailTv.text = email
//        } else {
//            //not ok
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
//    }
//
//    private fun addItemToList(snapshot: DataSnapshot) {
//        val items = snapshot.children.iterator()
//
//        if(items.hasNext()){
//            val shoppingIndexedValue = items.next()
//            val itemsIterator = shoppingIndexedValue.children.iterator()
//            while(itemsIterator.hasNext()){
//                val currentItem = itemsIterator.next()
//                val shoppingItemData = ItemsModel.createList()
//                val map = currentItem.value as HashMap<*, *>
//
//                shoppingItemData.UID = currentItem.key
//                shoppingItemData.done = map["done"] as Boolean?
//                shoppingItemData.itemDataText = map["itemDataText"] as String?
//                itemSList!!.add(shoppingItemData)
//            }
//        }
//
//        adapter.notifyDataSetChanged()
//    }
//
//    override fun modifyItem(UID: String, done: Boolean) {
//        val itemReference = FirebaseDatabase.getInstance().reference.child("items").child(UID)
//        val item = HashMap<String, Any>()
//        item["done"] = done
//        itemReference.updateChildren(item)
//
//        // Find the item in the itemList and update its done value
//        val itemToUpdate = itemSList?.find { it.UID == UID }
//        itemToUpdate?.done = done
//        // Notify the adapter that the data has changed to update the ListView
//        adapter.notifyDataSetChanged()
//
//        //itemReference.child("done").setValue(isDone)
//    }
//
//    override fun onItemDelete(UID: String) {
//        val itemReference = FirebaseDatabase.getInstance().reference.child("items").child(UID)
//        itemReference.removeValue()
//
//        // Find the item in the itemList and remove it
//        val itemToRemove = itemSList?.find { it.UID == UID }
//        itemSList?.remove(itemToRemove)
//        // Notify the adapter that the data has changed to update the ListView
//        adapter.notifyDataSetChanged()
//
////        val itemReference = database.child("items").child(itemUID)
////        itemReference.removeValue()
////        adapter.notifyDataSetChanged()
//    }
//}