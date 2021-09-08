package com.example.campusfriend01.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {
    companion object{

        private val database = Firebase.database

        //board라는 곳에 저장할 것이다.
        val boardRef = database.getReference("board")

        val commentRef = database.getReference("comment")


        //FB의 realtime DB에서 club이라는 곳에 저장할 것이다
        val clubRef = database.getReference("club")

        //message 이거 맞나??
        val chatRef = database.getReference("chat")
    }
}