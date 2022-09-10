package com.jdm.trustme.model.response

import com.jdm.trustme.model.entity.Food
import com.jdm.trustme.model.entity.Store

data class Feed(
    val food: Food,
    val store: Store
)
