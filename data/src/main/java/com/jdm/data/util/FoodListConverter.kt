package com.jdm.trustme.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdm.data.model.entity.Food

class FoodListConverter {
    @TypeConverter
    fun fromString(value: String): List<Food>? {
        val listType = object : TypeToken<List<Food>>() {}.type
        return Gson().fromJson<List<Food>>(value, listType)
    }
    @TypeConverter
    fun fromList(list: List<Food>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}