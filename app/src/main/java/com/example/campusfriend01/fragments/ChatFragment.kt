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
import com.example.campusfriend01.board.BoardListLVAdapter
import com.example.campusfriend01.board.BoardModel
import com.example.campusfriend01.chat.ChatActivity
import com.example.campusfriend01.chat.PeopleListLVAdapter
import com.example.campusfriend01.chat.PeopleModel
import com.example.campusfriend01.club.ClubModel
import com.example.campusfriend01.databinding.FragmentChatBinding
import com.example.campusfriend01.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ChatFragment : Fragment() {

    private lateinit var binding : FragmentChatBinding


    private val peopleDataList = mutableListOf<PeopleModel>()
    private val peopleKeyList = mutableListOf<String>()

    private val TAG = ChatFragment::class.java.simpleName

    private lateinit var peopleRVAdapter : PeopleListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false )

        //어뎁터 연결하기
        peopleRVAdapter = PeopleListLVAdapter(peopleDataList)
        binding.peopleList.adapter = peopleRVAdapter

        binding.peopleList.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("key", peopleKeyList[position])

            startActivity(intent)
        }

        //임시로 만든 채팅창 이동 버튼 / test용
        binding.nextPage.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            startActivity(intent)
        }


        binding.boardTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_boardFragment)
        }
        binding.clubTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_clubFragment)

        }
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_homeFragment)

        }
        binding.settingTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_chatFragment_to_settingFragment)

        }

        getFBClubData()

        return binding.root
    }

    private fun getFBClubData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                peopleDataList.clear()

                for (dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())

                    dataModel.key

                    val item = dataModel.getValue(PeopleModel::class.java)
                    peopleDataList.add(item!!)
                    peopleKeyList.add(dataModel.key.toString())
                }

                peopleKeyList.reverse()
                peopleDataList.reverse()

                peopleRVAdapter.notifyDataSetChanged()

                Log.d(TAG, peopleDataList.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.chatRef.addValueEventListener(postListener)
    }


}



