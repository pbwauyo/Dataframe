package com.example.dataframe.api.service

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    companion object {
        const val BASE_URL = "http://tela.planetsystems.co:8080/"
    }

    @POST("hiv")
    suspend fun postHIVDetails(@Body requestBody: RequestBody): ResponseBody

    @POST("infrastructure")
    suspend fun postInfrastructureDetails(@Body requestBody: RequestBody): ResponseBody

    @POST("non-teaching-staff")
    suspend fun postNonTeachingStaffDetails(@Body requestBody: RequestBody): ResponseBody

    @POST("school-details")
    suspend fun postSchoolDetails(@Body requestBody: RequestBody): ResponseBody

    @POST("sports-details")
    suspend fun postSportsDetails(@Body requestBody: RequestBody): ResponseBody

    @POST("learner-details")
    suspend fun postLearnerDetails(@Body requestBody: RequestBody): ResponseBody

    @POST("teacher-details")
    suspend fun postTeacherDetails(@Body requestBody: RequestBody): ResponseBody

    @POST("teaching-materials")
    suspend fun postTeachingMaterialDetails(@Body requestBody: RequestBody): ResponseBody
}