package com.jdm.trustme.model.entity

import androidx.room.Entity
import javax.annotation.concurrent.Immutable

@Immutable
@Entity(primaryKeys = [("id")])
data class Store(
    var id: Int,
    var name: String,
    var img: String
)