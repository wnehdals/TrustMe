package com.jdm.data.model.entity.response

import com.jdm.data.model.entity.Food
import com.jdm.data.model.entity.Store

data class Feed(
    val food: Food,
    val store: Store
)
