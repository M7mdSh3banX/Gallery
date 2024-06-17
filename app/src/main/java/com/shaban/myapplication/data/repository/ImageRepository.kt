package com.shaban.myapplication.data.repository


interface ImageRepository {
    fun loadImagesFromInternet(): List<String>
    fun loadImagesFromDisk(): List<String>
    fun loadImagesFromResources(): List<Int>
}