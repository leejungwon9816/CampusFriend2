package com.example.campusfriend01.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.campusfriend01.MainActivity
import com.example.campusfriend01.R
import com.example.campusfriend01.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_join)


// Initialize Firebase Auth
        auth = Firebase.auth

        binding=DataBindingUtil.setContentView(this,R.layout.activity_join)

        binding.joinBtn.setOnClickListener {

            var isGoToJoin = true

            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()
            val nickname = binding.nicknameArea.text.toString()

            if(email.isEmpty()) {
                Toast.makeText(this, "email을 입력해주세요", Toast.LENGTH_SHORT).show()
                var isGoToJoin = false

            }
            if(password1.isEmpty()) {
                Toast.makeText(this, "password를 입력해주세요", Toast.LENGTH_SHORT).show()
                var isGoToJoin = false
            }
            if(password2.isEmpty()) {
                Toast.makeText(this, "password check를 입력해주세요", Toast.LENGTH_SHORT).show()
                var isGoToJoin = false
            }
            if(nickname.isEmpty()) {
                Toast.makeText(this, "nickname을 입력해주세요", Toast.LENGTH_SHORT).show()
                var isGoToJoin = false
            }
            if (!password1.equals(password2)) {
                Toast.makeText(this, "비밀번호를 동일하게 입력해주새요", Toast.LENGTH_SHORT).show()
                var isGoToJoin = false
            }
            if(password1.length < 6) {
                Toast.makeText(this, "비밀번호를 길게 입력해주세요", Toast.LENGTH_SHORT).show()
                var isGoToJoin = false
            }
            if( isGoToJoin) {
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,MainActivity::class.java)
                            // 로그인 성공 후 기존 activity를 다 날려버림.
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP  or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()

                        }
                    }
            }

        }


    }
}