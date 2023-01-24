package com.example.shoppingapp

data class ToDoItem (var id:String="" , var level:String="", var status:Boolean=false){

    fun statusChanged(){
        status=!status
    }
}