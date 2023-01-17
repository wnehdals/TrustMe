package com.jdm.trustme.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdm.data.model.entity.Store

class StoreListConverter {
    @TypeConverter
    fun fromString(value: String): List<Store> {
        val listType = object : TypeToken<List<Store>>() {}.type
        return Gson().fromJson<List<Store>>(value, listType)
    }
    @TypeConverter
    fun fromList(list: List<Store>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}