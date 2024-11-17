package com.ddanddan.watch.data.repository

import com.ddanddan.watch.data.datasource.DdanDdanRemoteDataSource
import com.ddanddan.watch.data.request.RequestDailyCalorie
import com.ddanddan.watch.data.response.toMainPet
import com.ddanddan.watch.data.response.toUser
import com.ddanddan.watch.data.response.toUserDailyInfo
import com.ddanddan.watch.domain.model.MainPet
import com.ddanddan.watch.domain.model.User
import com.ddanddan.watch.domain.model.UserDailyInfo
import com.ddanddan.watch.domain.repository.DdanDdanRepository
import javax.inject.Inject

class DdanDdanRepositoryImpl @Inject constructor(
    private val ddanDdanRemoteDataSource: DdanDdanRemoteDataSource
) : DdanDdanRepository {
    override suspend fun getUser(): User {
        return ddanDdanRemoteDataSource.getUser().toUser()
    }

    override suspend fun getMainPet(): MainPet {
        return ddanDdanRemoteDataSource.getMainPet().toMainPet()
    }

    override suspend fun patchDailyCalorie(requestBody: RequestDailyCalorie): UserDailyInfo {
        return ddanDdanRemoteDataSource.patchDailyCalorie(requestBody = requestBody).toUserDailyInfo()
    }
}
