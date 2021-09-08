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
import com.example.campusfriend01.databinding.FragmentBoardBinding
import com.example.campusfriend01.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BoardFragment : Fragment() {

    private lateinit var binding : FragmentBoardBinding

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = BoardFragment::class.java.simpleName

    private lateinit var boardRVAdapter : BoardListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false )

        //어뎁터 연결해주기
       /* val boardList = mutableListOf<BoardModel>()
        boardList.add(BoardModel("a","b","11","1212"))*/
        boardRVAdapter = BoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter

        binding.boardListView.setOnItemClickListener { parent, view, position, id ->

            // 첫번째 방법은 listview에 있는 데이터 title,content,time 다 다른 액티비티로 전달해줘서 만들기 (1개씩)
            /*val intent = Intent(context,BoardInsideActivity::class.java)
            intent.putExtra("title", boardDataList[position].title)
            intent.putExtra("content",boardDataList[position].content)
            intent.putExtra("time",boardDataList[position].time)
            startActivity(intent)*/


            // 두번째 방법은 FB에 있는 board에 대한 데이터의 id를 기반으로 다시 데이터를 받아오는 방법(묶음)
            val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }

        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
        }
        binding.clubTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_clubFragment)

        }
        binding.chatTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_chatFragment)

        }
        binding.settingTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_settingFragment)

        }
        getFBBoardData()

        return binding.root

    }

    private fun getFBBoardData() {
        val postListener = object  : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for(dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())

                    dataModel.key

                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }

                boardKeyList.reverse()
                boardDataList.reverse()
                boardRVAdapter.notifyDataSetChanged()

                Log.d(TAG, boardDataList.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)
    }
}