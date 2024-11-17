package com.ddanddan.watch.data.di

import com.ddanddan.watch.data.repository.DdanDdanRepositoryImpl
import com.ddanddan.watch.data.repository.HealthServicesRepositoryImpl
import com.ddanddan.watch.data.repository.PassiveDataRepositoryImpl
import com.ddanddan.watch.domain.repository.DdanDdanRepository
import com.ddanddan.watch.domain.repository.HealthServicesRepository
import com.ddanddan.watch.domain.repository.PassiveDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindHealthServicesRepository(healthServicesRepositoryImpl: HealthServicesRepositoryImpl): HealthServicesRepository

    @Singleton
    @Binds
    fun bindPassiveDataRepository(passiveDataRepositoryImpl: PassiveDataRepositoryImpl): PassiveDataRepository

    @Singleton
    @Binds
    fun bindDdanDdanRepository(ddanddanRepositoryImpl: DdanDdanRepositoryImpl): DdanDdanRepository
}