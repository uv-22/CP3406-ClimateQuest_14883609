package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.di

import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.OpenMeteoWeatherRepository
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        repository: OpenMeteoWeatherRepository
    ): WeatherRepository
}