package com.cmy.common.repository

/**
 * @author pokerfaceCmy
 * @date 2020/7/29
 * @description 基础的用户信息
 * @email cheng.meng.yuan@qq.com
 */

class UserInfo {
    object LoginBy {
        const val QQ = "qq"
        const val WECHAT = "wechat"
        const val WEIBO = "weibo"
        const val ALI = "ali"
        const val PHONE = "phone"
    }


    /**
     * 用户id(唯一)
     */
    var uid: Long = 0

    /**
     * 用户手机号
     */
    var phoneNumber: Long? = null

    /**
     * 用户的设备信息
     */
    lateinit var systemInfo: SystemInfo

    /**
     * 是否是vip用户
     */
    var isVip: Boolean = false

    /**
     * 是否关闭所有广告
     */
    var isCloseAd: Boolean = false

    /**
     * 上次登陆的方式
     */
    var loginBy: String? = null

}