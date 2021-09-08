package com.example.campusfriend01.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.campusfriend01.R
import com.example.campusfriend01.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private lateinit var binding : FragmentSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false )

        binding.boardTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingFragment_to_boardFragment)
        }
        binding.clubTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingFragment_to_clubFragment)

        }
        binding.chatTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_chatFragment)

        }
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingFragment_to_homeFragment)

        }

        return binding.root

    }
}