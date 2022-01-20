package com.example.decorating

abstract class CondimentDecorator: Beverage() {

    abstract fun getCondiment(): Beverage
    abstract fun setCondiment(pBeverage: Beverage)
}