package com.example.decorating.condiment

import com.example.decorating.Beverage
import com.example.decorating.CondimentDecorator

class Whip(private var mBeverage: Beverage): CondimentDecorator() {


    override fun getCondiment(): Beverage {
        return mBeverage
    }

    override fun setCondiment(pBeverage: Beverage) {
        mBeverage = pBeverage
    }

    override fun getDescription(): String {
        return mBeverage.getDescription() + ",${this.initName()}"
    }

    override fun cost(): Double {
        return 0.10 + mBeverage.cost()
    }

    override fun initName():String {
        return setDescription(this)
    }
}