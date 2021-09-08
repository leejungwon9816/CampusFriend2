package com.example.campusfriend01.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.campusfriend01.R
import com.example.campusfriend01.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false )

        binding.boardTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
        }
        binding.clubTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_clubFragment)

        }
        binding.chatTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_chatFragment)

        }
        binding.settingTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_settingFragment)

        }

       return binding.root
    }


}