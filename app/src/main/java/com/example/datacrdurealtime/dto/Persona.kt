package com.example.datacrdurealtime.dto

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Persona(val id: String? = null, val desc: String? = null,val nome:String?=null):Serializable

