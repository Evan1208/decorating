package com.example.decorating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.example.decorating.condiment.Mocha
import com.example.decorating.condiment.Soy
import com.example.decorating.condiment.Whip
import com.example.decorating.databinding.ActivityMainBinding
import com.example.decorating.databinding.ViewHThreeWeightBinding
import com.example.decorating.drinks.DarkRoast
import com.example.decorating.drinks.Espresso
import com.example.decorating.drinks.HoseBlend

class MainActivity : AppCompatActivity() {



    private val mBinding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val mArrayCoffeeTypeStr = arrayListOf<String>()
    private val mArrayCondimentTypeStr = arrayListOf<String>()
    private var mBeverage:Beverage? = null

    private val mCoffeeType = 0
    private val mCondimentType = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initView()
        var iBeverage:Beverage = Espresso()
        iBeverage = Mocha(iBeverage)
        iBeverage = Whip(iBeverage)
        iBeverage = Soy(iBeverage)

        Log.v("aaa", "Beverage  = ${iBeverage.getDescription()}")
        Log.v("aaa", "cost      = ${iBeverage.cost()}")


//        iBeverage = getCondimentDecorator(getName(Whip::class.java), iBeverage)!!
//
//        Log.v("aaa", "RemoveMocha  = ${iBeverage.getDescription()}")
//        Log.v("aaa", "cost      = ${iBeverage.cost()}")
//
//        iBeverage = getCondimentDecorator(getName(Whip::class.java), iBeverage)!!
//
//        Log.v("aaa", "RemoveWhip  = ${iBeverage.getDescription()}")
//        Log.v("aaa", "cost      = ${iBeverage.cost()}")
//
//        iBeverage = getCondimentDecorator(getName(Soy::class.java), iBeverage)!!
//
//        Log.v("aaa", "RemoveSoy  = ${iBeverage.getDescription()}")
//        Log.v("aaa", "cost      = ${iBeverage.cost()}")



        var iDarkRoast:Beverage = DarkRoast()
        iDarkRoast = Mocha(iDarkRoast)
        iDarkRoast = Mocha(iDarkRoast)
        iDarkRoast = Whip(iDarkRoast)
        iDarkRoast = Soy(iDarkRoast)


        Log.v("aaa", "Beverage  = ${iDarkRoast.getDescription()}")
        Log.v("aaa", "cost      = ${iDarkRoast.cost()}")
    }


    private fun getCondimentDecorator(pFindKey: String, pBeverage: Beverage): Beverage? {

        var iNowIndex = pBeverage
        var iPreIndex:CondimentDecorator? = null

        do {
            if( iNowIndex.javaClass.simpleName == pFindKey) {
                Log.v("aaa","find key = $pFindKey")
                break
            } else {
                if( iNowIndex is CondimentDecorator) {
                    iPreIndex = iNowIndex
                    iNowIndex = iNowIndex.getCondiment()
                } else {
                    Toast.makeText(this, "無此品項", Toast.LENGTH_SHORT).show()
                    return null
                }
            }
        }while (true)

        return if (pBeverage == iNowIndex) {
            if (iNowIndex is CondimentDecorator) {
                iNowIndex.getCondiment()
            } else {
                null
            }
        } else {
            if (iNowIndex is CondimentDecorator) {
                iPreIndex?.setCondiment(iNowIndex.getCondiment())
            }
            pBeverage
        }
    }

    private fun initView() {
        coffeeType()
        condimentType()
        showCondimentOrCoffee(mCoffeeType)
        setViewWithCoffeeAndCondiment(
            mBinding.mCoffeeIc,
            mArrayCoffeeTypeStr,
            mCoffeeTypeClick,
        )
        setViewWithCoffeeAndCondiment(mBinding.mCondimentIc, mArrayCondimentTypeStr,
            mCondimentTypeClick, mCondimentRemoveTypeClick)

        setCoffeeOrCondimentView(mBinding.mCoffeeBt, mCoffeeType, mCoffeeOrCondimentTypeClick)
        setCoffeeOrCondimentView(mBinding.mCondimentBt, mCondimentType, mCoffeeOrCondimentTypeClick)
        mBinding.mEnterBt.setOnClickListener {
            mBeverage = null
            showOrder(mBeverage)
            showCondimentOrCoffee(mCoffeeType)
        }
    }

    private fun setCoffeeOrCondimentView(pView: AppCompatButton, pShowType: Int, pClick: View.OnClickListener) {
        pView.tag = pShowType
        pView.setOnClickListener(pClick)
    }

    private fun setViewWithCoffeeAndCondiment(
        pViewType: ViewHThreeWeightBinding,
        pTypeArrayStr: ArrayList<String>,
        pTypeClick: View.OnClickListener,
        pCondimentRemoveTypeClick: View.OnLongClickListener? = null
    ) {
        pViewType.mTypeOne.text = pTypeArrayStr[0]
        pViewType.mTypeTwo.text = pTypeArrayStr[1]
        pViewType.mTypeThree.text = pTypeArrayStr[2]

        pViewType.mTypeOne.tag = pTypeArrayStr[0]
        pViewType.mTypeTwo.tag = pTypeArrayStr[1]
        pViewType.mTypeThree.tag = pTypeArrayStr[2]
        pViewType.mTypeOne.setOnClickListener(pTypeClick)
        pViewType.mTypeTwo.setOnClickListener(pTypeClick)
        pViewType.mTypeThree.setOnClickListener(pTypeClick)
        pCondimentRemoveTypeClick?.let {
            pViewType.mTypeOne.setOnLongClickListener(it)
            pViewType.mTypeTwo.setOnLongClickListener(it)
            pViewType.mTypeThree.setOnLongClickListener(it)
        }
    }

    private fun condimentType() {
        mArrayCondimentTypeStr.clear()
        mArrayCondimentTypeStr.add(getName(Soy::class.java))
        mArrayCondimentTypeStr.add(getName(Whip::class.java))
        mArrayCondimentTypeStr.add(getName(Mocha::class.java))
    }

    private fun coffeeType() {
        mArrayCoffeeTypeStr.clear()
        mArrayCoffeeTypeStr.add(getName(DarkRoast::class.java))
        mArrayCoffeeTypeStr.add(getName(Espresso::class.java))
        mArrayCoffeeTypeStr.add(getName(HoseBlend::class.java))
    }

    private fun getName(pClass: Class<*>): String {
        return pClass.simpleName
    }


    private fun showOrder(pBeverage: Beverage?) {
        mBinding.mCostTv.text = pBeverage?.cost()?.toString() ?: ""
        mBinding.mContentTv.text = pBeverage?.getDescription() ?: ""
    }

    private fun showCondimentOrCoffee(pShowType: Int) {
        if( pShowType == mCondimentType) {
            mBinding.mCoffeeIc.root.visibility = View.GONE
            mBinding.mCondimentIc.root.visibility = View.VISIBLE
        } else {
            mBinding.mCoffeeIc.root.visibility = View.VISIBLE
            mBinding.mCondimentIc.root.visibility = View.GONE
        }
    }

    private val mCoffeeOrCondimentTypeClick = View.OnClickListener {
        val iTag = it.tag
        if( iTag != null && iTag is Int) {
            showCondimentOrCoffee( iTag)
        }
    }

    private val mCoffeeTypeClick = View.OnClickListener {
        val iTag = it.tag
        if( iTag != null && iTag is String) {
            mBeverage = when (iTag) {
                getName(DarkRoast::class.java) -> {
                    DarkRoast()
                }
                getName(Espresso::class.java) -> {
                    Espresso()
                }
                getName(HoseBlend::class.java) -> {
                    HoseBlend()
                }
                else -> {
                    null
                }
            }
            showOrder(mBeverage)
            showCondimentOrCoffee(mCondimentType)
        }
    }

    private val mCondimentTypeClick  = View.OnClickListener {
        val iTag = it.tag
        if( iTag != null && iTag is String) {
            mBeverage?.let {itB ->
                val iBeverage = when (iTag) {
                    getName(Soy::class.java) -> {
                        Soy(itB)
                    }
                    getName(Whip::class.java) -> {
                        Whip(itB)
                    }
                    getName(Mocha::class.java) -> {
                        Mocha(itB)
                    }
                    else -> {
                        null
                    }
                }
                mBeverage = iBeverage
                showOrder(mBeverage)
            } ?: kotlin.run {
                Toast.makeText(this, "還未選擇咖啡", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val mCondimentRemoveTypeClick = View.OnLongClickListener {
        val iTag = it.tag
        if( iTag != null && iTag is String) {
            mBeverage?.let { itB ->
                val iBeverage = getCondimentDecorator(iTag, itB)
                if( iBeverage != null) {
                    mBeverage = iBeverage
                    showOrder(mBeverage)
                }
            }
        }


        true
    }

}