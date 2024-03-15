package com.learning.agrovision.API

import com.learning.agrovision.Model.FertilizerInputModel
import com.learning.agrovision.Model.FertilizerOutputModel
import com.learning.agrovision.Model.YeidlOutputModel
import com.learning.agrovision.Model.YeildInputModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/predict")
    suspend fun fertilizer(@Body request: FertilizerInputModel): Response<FertilizerOutputModel>
    @POST("/yeild")
    suspend fun cropYield(@Body request: YeildInputModel): Response<YeidlOutputModel>


}