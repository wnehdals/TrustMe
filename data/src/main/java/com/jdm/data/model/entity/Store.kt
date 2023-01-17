package com.jdm.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.concurrent.Immutable

@Immutable
@Entity
data class Store(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String,
    var img: Int
)