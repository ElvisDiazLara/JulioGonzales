package com.example.juliogonzales.Models

data class SummaryReportModel(
    val propertyName:String = "",
    val id: String? = "",
    val vehicleNumber:String = "",
    val licence: String = "",
    val securityPerson: String = "",
    val email: String = "",
    val date: String = "",
    val sealNumber: String = "",
    val personAfixed: String = "",
    val personVerified: String = "",
    val personArrival: String = "",
    val image: String = "")