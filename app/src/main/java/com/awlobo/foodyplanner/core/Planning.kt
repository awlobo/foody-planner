package com.awlobo.foodyplanner.core

import com.awlobo.foodyplanner.Comida

data class Planning(
    val name:String = "plan",
    var foodList: MutableMap<String, Comida> = mutableMapOf()

)