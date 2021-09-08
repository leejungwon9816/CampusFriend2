package com.example.campusfriend01.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.campusfriend01.R

class PeopleListLVAdapter(val peopleList :MutableList<PeopleModel> ) : BaseAdapter() {
    override fun getCount(): Int {
        return peopleList.size
    }

    override fun getItem(position: Int): Any {
        return peopleList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        view = LayoutInflater.from(parent?.context).inflate(R.layout.chat_list_item,parent,false)

        val itemLinearLayoutView = view?.findViewById<LinearLayout>(R.id.itemView)

        val title = view?.findViewById<TextView>(R.id.nickname)
        val content = view?.findViewById<TextView>(R.id.content)
        val time = view?.findViewById<TextView>(R.id.time)

        title!!.text=peopleList[position].nickname
        content!!.text=peopleList[position].content
        time!!.text=peopleList[position].time

        return view!!
    }


}