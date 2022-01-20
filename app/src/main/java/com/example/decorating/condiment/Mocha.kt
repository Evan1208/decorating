package com.example.decorating.condiment

import com.example.decorating.Beverage
import com.example.decorating.CondimentDecorator

class Mocha(private var mBeverage: Beverage): CondimentDecorator() {

    override fun getCondiment(): Beverage {
        return mBeverage
    }

    override fun setCondiment(pBeverage: Beverage) {
        mBeverage = pBeverage
    }

    override fun getDescription(): String {
        return mBeverage.getDescription() + "," + super.getDescription()
    }

//    override fun getCondimentDescription(): String {
//        return mBeverage.getDescription() + "---\n" + getDescription()
//    }

    override fun cost(): Double {
        return 0.20 + mBeverage.cost()
    }

    override fun initName() {
        setDescription(this)
    }
}