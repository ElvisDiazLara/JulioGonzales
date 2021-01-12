package com.example.juliogonzales.Auxiliaries

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import com.example.juliogonzales.R

fun shortToast(msg: String, context: Context) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun longToast(msg: String, context: Context) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

fun getInstuction(num : Int, context: Context) : String {
    val instructions_arr = context.resources.getStringArray(R.array.instructions)
    return instructions_arr[num]
}

fun getInstuctionDescription(num : Int, context: Context) : String {
    val instructions_arr = context.resources.getStringArray(R.array.instructions_descriptions)
    return instructions_arr[num]
}