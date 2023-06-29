package com.gxx.networkproject.demorequesttwo.basemodel

import com.google.gson.JsonElement
import com.gxx.networklibrary.networkpackge.inter.OnIParserListener

class BaseModel(
    var method: String? = null,
    var resourceJsonString: String? = null,
    var targetJsonElement: JsonElement? = null
) : OnIParserListener {
    var message: String? = null
    var nu: String? = null
    var ischeck: String? = null
    var com: String? = null
    var status: String? = null
    var condition: String? = null
    var state: String? = null

    override fun onTargetJsonElement(): JsonElement? {
        return targetJsonElement
    }

    override fun onResourceJsonString(): String? {
        return resourceJsonString
    }

    override fun isSuccess(): Boolean {
       return status == "200"
    }

}