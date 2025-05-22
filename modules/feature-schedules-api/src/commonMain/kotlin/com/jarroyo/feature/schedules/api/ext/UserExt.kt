package com.jarroyo.feature.schedules.api.ext

import com.jarroyo.feature.schedules.api.model.User

fun User.getFullName() = "$name $lastname"
