package com.example.dataframe.data.repositories

import com.example.dataframe.api.service.ApiService
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun saveHIVDetails(requestBody: RequestBody): ResponseBody {
        return apiService.postHIVDetails(requestBody)
    }

    suspend fun saveInfrastructureDetails(requestBody: RequestBody): ResponseBody {
        return apiService.postInfrastructureDetails(requestBody)
    }

    suspend fun saveNonTeachingStaffDetails(requestBody: RequestBody): ResponseBody {
        return apiService.postNonTeachingStaffDetails(requestBody)
    }

    suspend fun saveSchoolDetails(requestBody: RequestBody): ResponseBody {
        return apiService.postSchoolDetails(requestBody)
    }

    suspend fun saveSportsDetails(requestBody: RequestBody): ResponseBody {
        return apiService.postSportsDetails(requestBody)
    }

    suspend fun saveLearnerDetails(requestBody: RequestBody): ResponseBody {
        return apiService.postLearnerDetails(requestBody)
    }

    suspend fun saveTeacherDetails(requestBody: RequestBody): ResponseBody {
        return apiService.postTeacherDetails(requestBody)
    }

    suspend fun saveTeachingMaterialDetails(requestBody: RequestBody): ResponseBody {
        return apiService.postTeachingMaterialDetails(requestBody)
    }
}