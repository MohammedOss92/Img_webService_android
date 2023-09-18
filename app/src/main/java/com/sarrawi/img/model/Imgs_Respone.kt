package com.sarrawi.img.model

import com.google.gson.annotations.SerializedName

data class Imgs_Respone(@SerializedName("Imgs_Model")
                        val results:List<Imgs_Model>)
