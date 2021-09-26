package com.example.dataframe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.dataframe.databinding.FragmentStudentBinding
import com.example.dataframe.ui.viewmodels.AppViewModel
import com.example.dataframe.utils.CustomToast
import com.example.dataframe.utils.Utility
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@AndroidEntryPoint
class StudentFragment : Fragment() {
    private var binding: FragmentStudentBinding? = null
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
        binding = FragmentStudentBinding.inflate(inflater, container, false)
        navController = findNavController()

        observeProgress()
        observeResponse()
        observerResponseMessages()

        binding!!.submitBtn.setOnClickListener {
            submitDetails()
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun submitDetails() {
        val jsonObject = JSONObject()
        jsonObject.put("test", "")
        val requestBody = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        appViewModel.saveLearnerDetails(requestBody)
    }

    private fun observerResponseMessages() {
        appViewModel.responseMessageLiveData.observe(viewLifecycleOwner) {
            it.getContent()?.let { message ->
                customToast.showLongToast(message)
            }
        }
    }

    private fun observeProgress() {
        appViewModel.showProgressLiveData.observe(viewLifecycleOwner) { showProgress ->
            if (showProgress) {
                kProgressHUD.show()
            } else {
                kProgressHUD.dismiss()
            }
        }
    }

    private fun observeResponse() {
    }
}