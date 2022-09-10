package com.jdm.trustme.util

import com.jdm.trustme.R

object ColorUtil {
    fun getCircleDrawable(idx: Int): Int {
        val colorList = arrayOf<Int>(
            R.drawable.bg_cir_7ab16f,
            R.drawable.bg_cir_4447c4,
            R.drawable.bg_cir_0be14c,
            R.drawable.bg_cir_575571,
            R.drawable.bg_cir_c350a3,
            R.drawable.bg_cir_e44a63,
            R.drawable.bg_cir_a4d8fa,
            R.drawable.bg_cir_fbbde4,
            R.drawable.bg_cir_96b188,
            R.drawable.bg_cir_3c7ede,
        )
        //val range = (0..colorList.size-1)
        return colorList[idx]
    }
    fun getRandomIdx(): Int {
        val colorList = arrayOf<Int>(
            R.drawable.bg_cir_7ab16f,
            R.drawable.bg_cir_4447c4,
            R.drawable.bg_cir_0be14c,
            R.drawable.bg_cir_575571,
            R.drawable.bg_cir_c350a3,
            R.drawable.bg_cir_e44a63,
            R.drawable.bg_cir_a4d8fa,
            R.drawable.bg_cir_fbbde4,
            R.drawable.bg_cir_96b188,
            R.drawable.bg_cir_3c7ede,
        )
        val range = (0..colorList.size-1)
        return range.random()
    }
}