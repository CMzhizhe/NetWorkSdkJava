package com.gxx.networkproject.constant

object Constant {
    const val BASE_REQUEST = "https://jsonplaceholder.typicode.com/"

    const val BASE_REQUEST_V2 = "https://www.kuaidi100.com/"

    /**
     * @date 创建时间 2019/8/3
     * @author gaoxiaoxiong
     * @desc 返回baseUrl的请求
     */
    fun getBaseRequest(method: String): String {
        check(!method.startsWith("/")) { "方法名，不可以 '/' 开头" }
        return String.format(
            "%s%s",
            "${BASE_REQUEST}",
            method
        )
    }

    fun getBaseRequestV2(method: String): String {
        check(!method.startsWith("/")) { "方法名，不可以 '/' 开头" }
        return String.format(
            "%s%s",
            "${BASE_REQUEST_V2}",
            method
        )
    }
}