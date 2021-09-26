package com.example.dataframe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.dataframe.databinding.FragmentSchoolsListBinding
import com.example.dataframe.ui.viewmodels.AppViewModel
import com.example.dataframe.utils.CustomToast
import com.example.dataframe.utils.Utility
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolsListFragment : Fragment() {
    private var binding: FragmentSchoolsListBinding? = null
    private lateinit var navController: NavController
    private lateinit var kProgressHUD: KProgressHUD
    private val appViewModel: AppViewModel by viewModels()
    private lateinit var customToast: CustomToast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kProgressHUD = Utility.getDefaultProgressIndicator(requireActivity())
        customToast = CustomToast(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSchoolsListBinding.inflate(inflater, container, false)
        navController = findNavController()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}