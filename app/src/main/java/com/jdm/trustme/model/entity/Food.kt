package com.jdm.trustme.model.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.concurrent.Immutable

@Immutable
@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var storeId: Long,
    var name: String,
    var content: String,
    var imgId: List<Long>
)
