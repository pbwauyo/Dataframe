package com.example.dataframe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.dataframe.databinding.FragmentInfrastructureBinding
import com.example.dataframe.utils.Utility
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfrastructureFragment : Fragment() {
    private var binding: FragmentInfrastructureBinding? = null
    private lateinit var navController: NavController
    private lateinit var kProgressHUD: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kProgressHUD = Utility.getDefaultProgressIndicator(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfrastructureBinding.inflate(inflater, container, false)
        navController = findNavController()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}