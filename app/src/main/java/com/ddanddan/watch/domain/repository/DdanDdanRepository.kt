package com.ddanddan.watch.domain.repository

import com.ddanddan.watch.data.request.RequestDailyCalorie
import com.ddanddan.watch.domain.model.MainPet
import com.ddanddan.watch.domain.model.User
import com.ddanddan.watch.domain.model.UserDailyInfo

/**
 * 워치 앱에서 다뤄질 api 갯수가 많지 않고 앞으로 더 늘어날 여지도 적어보여서 하나로 관리하고자 함
 * 추후 쪼개질 여지는 있음
 */
interface DdanDdanRepository {
    suspend fun getUser() : User
    suspend fun getMainPet() : MainPet
    suspend fun patchDailyCalorie(requestBody: RequestDailyCalorie) : UserDailyInfo
}
