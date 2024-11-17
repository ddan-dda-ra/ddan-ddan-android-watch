package com.ddanddan.watch.data.service

import com.ddanddan.watch.data.request.RequestDailyCalorie
import com.ddanddan.watch.data.response.ResponseDailyCalorie
import com.ddanddan.watch.data.response.ResponseMainPet
import com.ddanddan.watch.data.response.ResponseUser
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface DdanDdanService {
    /**
     * 내 정보 조회
     */
    @GET("/v1/user/me")
    suspend fun getUser(
    ): ResponseUser

    /**
     * 메인 펫 조회
     */
    @GET("/v1/user/me/main-pet")
    suspend fun getMainPet(
    ): ResponseMainPet

    /**
     * 일일 칼로리 갱신
     */
    @PATCH("/v1/user/me/daily-calorie")
    suspend fun patchDailyCalorie(
        @Body requestBody: RequestDailyCalorie
    ): ResponseDailyCalorie
}