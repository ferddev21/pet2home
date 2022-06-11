package com.capstone.pet2home.api.response

import com.google.gson.annotations.SerializedName

data class PredictPetRes(

	@field:SerializedName("Breed")
	val breed: String? = null,

	@field:SerializedName("Presentase")
	val presentase: String? = null,

	@field:SerializedName("error")
	val error: String? = null
)
