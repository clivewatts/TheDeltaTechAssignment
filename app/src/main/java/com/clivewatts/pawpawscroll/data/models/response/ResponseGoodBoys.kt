package com.clivewatts.pawpawscroll.data.models.response

import com.google.gson.annotations.SerializedName

data class ResponseGoodBoys (
	@SerializedName("message") val message : List<String?>?,
	@SerializedName("status") val status : String
)