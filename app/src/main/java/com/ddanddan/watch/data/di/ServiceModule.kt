package com.ddanddan.watch.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    //todo - 수정 예정
//    @Singleton
//    @Provides
//    fun provideSearchService(retrofit: Retrofit) = retrofit.create(SearchService::class.java)
}