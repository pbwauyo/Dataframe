package com.example.dataframe.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dataframe.data.repositories.AppRepository
import com.example.dataframe.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    private val _showProgressLiveData = MutableLiveData<Boolean>()
    val showProgressLiveData: LiveData<Boolean> = _showProgressLiveData

    private val _responseMessageLiveData = MutableLiveData<Event<String>>()
    val responseMessageLiveData: LiveData<Event<String>> = _responseMessageLiveData

    private val _responseLiveData = MutableLiveData<ResponseBody>()
    val responseLiveData: LiveData<ResponseBody> = _responseLiveData

    fun saveHIVDetails(requestBody: RequestBody) {
        _showProgressLiveData.value = true
        viewModelScope.launch {
            try {
                val response = appRepository.saveHIVDetails(requestBody)
                _responseLiveData.value = response
            } catch (e: Exception) {
                _responseMessageLiveData.value = Event("${e.message}")
            }
            _showProgressLiveData.value = false
        }
    }

    fun saveInfrastructureDetails(requestBody: RequestBody) {
        _showProgressLiveData.value = true
        viewModelScope.launch {
            try {
                val response = appRepository.saveInfrastructureDetails(requestBody)
                _responseLiveData.value = response
            } catch (e: Exception) {
                _responseMessageLiveData.value = Event("${e.message}")
            }
            _showProgressLiveData.value = false
        }
    }

    fun saveNonTeachingStaffDetails(requestBody: RequestBody) {
        _showProgressLiveData.value = true
        viewModelScope.launch {
            try {
                val response = appRepository.saveNonTeachingStaffDetails(requestBody)
                _responseLiveData.value = response
            } catch (e: Exception) {
                _responseMessageLiveData.value = Event("${e.message}")
            }
            _showProgressLiveData.value = false
        }
    }

    fun saveSchoolDetails(requestBody: RequestBody) {
        _showProgressLiveData.value = true
        viewModelScope.launch {
            try {
                val response = appRepository.saveSchoolDetails(requestBody)
                _responseLiveData.value = response
            } catch (e: Exception) {
                _responseMessageLiveData.value = Event("${e.message}")
            }
            _showProgressLiveData.value = false
        }
    }

    fun saveSportsDetails(requestBody: RequestBody) {
        _showProgressLiveData.value = true
        viewModelScope.launch {
            try {
                val response = appRepository.saveSportsDetails(requestBody)
                _responseLiveData.value = response
            } catch (e: Exception) {
                _responseMessageLiveData.value = Event("${e.message}")
            }
            _showProgressLiveData.value = false
        }
    }

    fun saveLearnerDetails(requestBody: RequestBody) {
        _showProgressLiveData.value = true
        viewModelScope.launch {
            try {
                val response = appRepository.saveLearnerDetails(requestBody)
                _responseLiveData.value = response
            } catch (e: Exception) {
                _responseMessageLiveData.value = Event("${e.message}")
            }
            _showProgressLiveData.value = false
        }
    }

    fun saveTeacherDetails(requestBody: RequestBody) {
        _showProgressLiveData.value = true
        viewModelScope.launch {
            try {
                val response = appRepository.saveTeacherDetails(requestBody)
                _responseLiveData.value = response
            } catch (e: Exception) {
                _responseMessageLiveData.value = Event("${e.message}")
            }
            _showProgressLiveData.value = false
        }
    }

    fun saveTeachingMaterialDetails(requestBody: RequestBody) {
        _showProgressLiveData.value = true
        viewModelScope.launch {
            try {
                val response = appRepository.saveTeachingMaterialDetails(requestBody)
                _responseLiveData.value = response
            } catch (e: Exception) {
                _responseMessageLiveData.value = Event("${e.message}")
            }
            _showProgressLiveData.value = false
        }
    }
}