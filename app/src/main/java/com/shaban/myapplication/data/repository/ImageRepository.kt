package com.shaban.myapplication.data.repository


interface ImageRepository {
    suspend fun loadImagesFromInternet(): List<String>
    suspend fun loadImagesFromDisk(): List<String>
    fun loadImagesFromResources(): List<Int>
}