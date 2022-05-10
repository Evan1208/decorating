package com.example.decorating.drinks

import com.example.decorating.Beverage

class Espresso: Beverage() {

    override fun cost(): Double {
        return 1.11
    }

    override fun initName():String {
        return setDescription(this)
    }

}