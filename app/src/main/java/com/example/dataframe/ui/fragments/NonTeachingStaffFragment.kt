package com.example.dataframe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.dataframe.databinding.FragmentNonTeachingStaffBinding
import com.example.dataframe.ui.viewmodels.AppViewModel
import com.example.dataframe.utils.CustomToast
import com.example.dataframe.utils.Utility
import com.google.android.material.datepicker.MaterialDatePicker
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*

@AndroidEntryPoint
class NonTeachingStaffFragment : Fragment() {
    private var binding: FragmentNonTeachingStaffBinding? = null
    private lateinit var navController: NavController
    private lateinit var kProgressHUD: KProgressHUD
    private val appViewModel: AppViewModel by viewModels()
    private lateinit var customToast: CustomToast
    private lateinit var datePicker: MaterialDatePicker<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kProgressHUD = Utility.getDefaultProgressIndicator(requireActivity())
        customToast = CustomToast(requireActivity())
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date of birth")
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNonTeachingStaffBinding.inflate(inflater, container, false)
        navController = findNavController()

        observeProgress()
        observeResponse()
        observerResponseMessages()

        binding!!.submit.setOnClickListener {
            submitDetails()
        }

        binding!!.dobDatePicker.setOnClickListener {
            selectDateOfBirth()
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
        appViewModel.saveNonTeachingStaffDetails(requestBody)
    }

    private fun observerResponseMessages() {
        appViewModel.responseMessageLiveData.observe(viewLifecycleOwner) {
            it.getContent()?.let { message ->
                customToast.showLongToast(message)
            }
        }
    }

    private fun selectDateOfBirth() {
        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            binding!!.birthDay.text = calendar.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
            val month = calendar.get(Calendar.MONTH).plus(1)
            binding!!.birthMonth.text = month.toString().padStart(2, '0')
            binding!!.birthYear.text = calendar.get(Calendar.YEAR).toString()
        }
        datePicker.show(requireActivity().supportFragmentManager, "DOB_DATE_PICKER")
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