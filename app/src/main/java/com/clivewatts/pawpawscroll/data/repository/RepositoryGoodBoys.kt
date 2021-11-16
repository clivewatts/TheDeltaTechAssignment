package com.clivewatts.pawpawscroll.data.repository

import com.clivewatts.pawpawscroll.data.models.response.ResponseGoodBoys

interface RepositoryGoodBoys {

    suspend fun fetchGoodBoys(pageSize : Int) : ResponseGoodBoys

}