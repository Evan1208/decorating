package com.example.decorating

abstract class CondimentDecorator: Beverage() {

    abstract override fun getDescription():String
    abstract fun getCondiment(): Beverage
    abstract fun setCondiment(pBeverage: Beverage)
}