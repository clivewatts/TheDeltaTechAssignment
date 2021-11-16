package com.clivewatts.pawpawscroll.data.repository

import com.clivewatts.pawpawscroll.data.models.response.ResponseGoodBoys
import com.clivewatts.pawpawscroll.data.service.ApiDogCeo


class RepositoryGoodBoysImpl(private val source: ApiDogCeo) : RepositoryGoodBoys {

    override suspend fun fetchGoodBoys(pageSize: Int): ResponseGoodBoys {
       return source.fetchGoodBoys(pageSize)
    }

}