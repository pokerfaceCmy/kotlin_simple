package com.cmy.common.repository.remote

/**
 * @author pokerfaceCmy
 * @date 2020/7/29
 * @description 网络数据请求
 * @email cheng.meng.yuan@qq.com
 */
class HttpManager private constructor() {

    //静态内部类实现线程安全的单例模式
    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = HttpManager()
    }


}