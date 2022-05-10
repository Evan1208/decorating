package com.example.decorating

import java.lang.StringBuilder

abstract class Beverage {

    init {
        //因為繼承的關係, 所以init呼叫後 導致mDescription資料並未建立
        // (因為使用lazy的關係), 因此需要延遲 但會出現一些延遲問題
//        android.os.Handler(Looper.getMainLooper()).postDelayed({
//            initName()
//        }, 200)
    }

    companion object {
        fun cleanBuilder(pDescription: StringBuilder) {
            pDescription.delete(0, pDescription.length)
        }
    }


    private val mDescription = StringBuilder()



    open fun getDescription(): String {
        if( mDescription.isEmpty()) {
            initName()
        }
        return mDescription.toString()
    }

    fun setDescription(pDescription: Beverage):String {
        cleanBuilder(mDescription)
        return mDescription.append(pDescription::class.java.simpleName).toString()
    }


    abstract fun cost(): Double
    abstract fun initName():String
}