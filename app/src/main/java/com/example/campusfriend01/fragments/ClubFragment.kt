package com.example.campusfriend01.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.campusfriend01.R
import com.example.campusfriend01.board.BoardInsideActivity
import com.example.campusfriend01.board.BoardListLVAdapter
import com.example.campusfriend01.board.BoardModel
import com.example.campusfriend01.board.BoardWriteActivity
import com.example.campusfriend01.club.ClubInsideActivity
import com.example.campusfriend01.club.ClubListLVAdapter
import com.example.campusfriend01.club.ClubModel
import com.example.campusfriend01.club.Club_WriteActivity
import com.example.campusfriend01.databinding.FragmentClubBinding
import com.example.campusfriend01.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ClubFragment : Fragment() {

    private lateinit var binding : FragmentClubBinding

    private val clubDataList = mutableListOf<ClubModel>()
    private val clubKeyList = mutableListOf<String>()

    private val TAG = ClubFragment::class.java.simpleName

    private lateinit var clubRVAdapter : ClubListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_club, container, false )

        //어뎁터 연결
        clubRVAdapter = ClubListLVAdapter(clubDataList)
        binding.clubListView.adapter = clubRVAdapter

        binding.clubListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, ClubInsideActivity::class.java)
            intent.putExtra("key", clubKeyList[position])
            startActivity(intent)
        }
        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, Club_WriteActivity::class.java)
            startActivity(intent)
        }


        binding.boardTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_clubFragment_to_boardFragment)
        }
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_clubFragment_to_homeFragment)

        }
        binding.chatTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_clubFragment_to_chatFragment)

        }
        binding.settingTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_clubFragment_to_settingFragment)

        }
        getFBClubData()

        return binding.root

    }
    private fun getFBClubData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                clubDataList.clear()

                for (dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())

                    dataModel.key

                    val item = dataModel.getValue(ClubModel::class.java)
                  clubDataList.add(item!!)
                    clubKeyList.add(dataModel.key.toString())
                }

                clubKeyList.reverse()
                clubDataList.reverse()
                clubRVAdapter.notifyDataSetChanged()

                Log.d(TAG, clubDataList.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.clubRef.addValueEventListener(postListener)
    }
}