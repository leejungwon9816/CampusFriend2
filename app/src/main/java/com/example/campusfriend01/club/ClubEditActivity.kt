package com.example.campusfriend01.club

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.campusfriend01.R
import com.example.campusfriend01.board.BoardEditActivity
import com.example.campusfriend01.board.BoardModel
import com.example.campusfriend01.databinding.ActivityClubEditBinding
import com.example.campusfriend01.utils.FBAuth
import com.example.campusfriend01.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.Exception

class ClubEditActivity : AppCompatActivity() {

    private lateinit var key: String

    private val TAG =BoardEditActivity::class.java.simpleName

    private lateinit var binding : ActivityClubEditBinding

    private lateinit var writerUid :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_club_edit)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_club_edit)

        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)
        editBoardData(key)

        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }
    }

    private fun editBoardData(key: String) {

        FBRef.boardRef
            .child(key)
            .setValue(
                BoardModel(binding.titleArea.text.toString(),
                binding.contentArea.text.toString(),
                writerUid,
                FBAuth.getTime())
            )

        Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show()

        finish()
    }

    private fun getImageData(key: String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

// ImageView in your Activity
        val imageViewFromFB = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {
            }
        })

    }

    private fun getBoardData(key : String) {

        val postListener = object  : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                    //Log.d(TAG, dataModel!!.title)

                    //EditText의 경우
                    binding.titleArea.setText(dataModel?.title)
                    binding.contentArea.setText(dataModel?.content)
                    writerUid = dataModel!!.uid


                } catch (e : Exception) {
                    Log.d(TAG, "삭제완료")
                }


            }


            override fun onCancelled(databaseError: DatabaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }

}