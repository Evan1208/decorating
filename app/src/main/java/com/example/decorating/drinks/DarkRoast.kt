package com.example.decorating.drinks

import com.example.decorating.Beverage

class DarkRoast: Beverage() {

    override fun cost(): Double {
        return 0.89
    }

    override fun initName() {
        setDescription(this)
    }
}