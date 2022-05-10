package com.jdm.trustme.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdm.trustme.model.entity.Goods

class GoodsListConverter {
    @TypeConverter
    fun fromString(value: String): List<Goods>? {
        val listType = object : TypeToken<List<Goods>>() {}.type
        return Gson().fromJson<List<Goods>>(value, listType)
    }
    @TypeConverter
    fun fromList(list: List<Goods>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}