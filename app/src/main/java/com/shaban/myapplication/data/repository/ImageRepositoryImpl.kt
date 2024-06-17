package com.shaban.myapplication.data.repository

import android.content.Context
import com.shaban.myapplication.R

class ImageRepositoryImpl(private val context: Context) : ImageRepository {

    override fun loadImagesFromInternet(): List<String> {
        val imageUrls = listOf(
            "https://i.pinimg.com/564x/cd/39/14/cd391457fd5f6c128e99c7e921606e11.jpg",
            "https://i.pinimg.com/564x/72/c7/a0/72c7a0a131f6ed77bfc7bf203ed47bf8.jpg",
            "https://i.pinimg.com/564x/a9/35/f2/a935f29af6e55366a0901ee9ab347fe4.jpg",
            "https://i.pinimg.com/564x/76/1b/b0/761bb0d24d7e98763a536709b3eff5bc.jpg",
            "https://i.pinimg.com/564x/9f/b3/fc/9fb3fcf85f22f1ebb40061039a7bf3bb.jpg",
            "https://i.pinimg.com/564x/2f/54/bf/2f54bfc97381b9bab0534abf480a3d90.jpg",
            "https://i.pinimg.com/564x/30/9f/ec/309fecf07c6707f3041164171a3510b3.jpg",
            "https://www.svgrepo.com/show/530618/hotel.svg",
            "https://www.svgrepo.com/show/530636/suv.svg",
            "https://www.svgrepo.com/show/530627/sunbathing.svg",
        )
        return imageUrls
    }

    override fun loadImagesFromDisk(): List<String> {
        return emptyList()
    }

    override fun loadImagesFromResources(): List<Int> {
        val imageIds = listOf(
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6,
            R.drawable.a7,
            R.drawable.a8,
            R.drawable.a9,
            R.drawable.a10,
        )
        return imageIds
    }
}
