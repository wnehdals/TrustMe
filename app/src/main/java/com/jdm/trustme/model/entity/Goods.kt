package com.jdm.trustme.model.entity

import androidx.room.Entity
import javax.annotation.concurrent.Immutable

@Immutable
@Entity(primaryKeys = [("id")])
data class Goods(
    var id: Int,
    var storeId: Int,
    var name: Int,
    var price: Int,
    var description: String,
    var img: String,
    var grade: Float
)
