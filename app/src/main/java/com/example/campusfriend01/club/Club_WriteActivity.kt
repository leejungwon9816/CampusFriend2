package com.example.campusfriend01.club

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.campusfriend01.R
import com.example.campusfriend01.board.BoardModel
import com.example.campusfriend01.databinding.ActivityClubWriteBinding
import com.example.campusfriend01.utils.FBAuth
import com.example.campusfriend01.utils.FBRef
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class Club_WriteActivity : AppCompatActivity() {


    private lateinit var binding:ActivityClubWriteBinding

    private val TAG = ActivityClubWriteBinding::class.java.simpleName

    private var isImageUpload = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_club_write)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_club_write)

       binding.writeBtn.setOnClickListener {
           // title, content를 FB 서버에 받아온다.
           val title = binding.titleArea.text.toString()
           val content = binding.contentArea.text.toString()
           val uid = FBAuth.getUid()
           val time = FBAuth.getTime()

           Log.d(TAG.toString(), title)
           Log.d(TAG.toString(), content)

           val key = FBRef.clubRef.push().key.toString()

           FBRef.clubRef
               .child(key)
               .setValue(ClubModel(title, content, uid, time))

           Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_SHORT).show()

           if(isImageUpload == true) {
               imageUpload(key)
           }

           //이 activity가 끝. (화면 사라짐)
           finish()
       }
        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload = true
        }

    }

    private fun imageUpload(key: String) {

        val storage = Firebase.storage
        // Create a storage reference from our app
        val storageRef = storage.reference
        // Create a reference to "mountains.jpg"
        val mountainsRef = storageRef.child(key+".png")

        val imageView = binding.imageArea
        // Get the data from an ImageView as bytes
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode==100){
            //작성 글에 이미지 넣기
            binding.imageArea.setImageURI(data?.data)
        }
    }
}