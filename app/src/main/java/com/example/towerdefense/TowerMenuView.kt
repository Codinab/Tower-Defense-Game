package com.example.towerdefense

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.towerdefense.utility.gameView
import com.example.towerdefense.utility.money
import com.example.towerdefense.utility.towerClicked

class TowerMenuView(context: Context) : RelativeLayout(context) {
    private lateinit var sell: ImageButton
    private lateinit var upgrade: ImageButton
    private lateinit var damageType: ImageButton
    private lateinit var sellNumber: TextView
    private lateinit var upgradeNumber: TextView
    
    init {
        setupButtons(context)
        setBackgroundResource(R.drawable.universal_background_h)
    }
    
    private fun setupButtons(context: Context) {
        upgrade = ImageButton(context).apply {
            id = generateViewId()
            alpha = 0.8f
            setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.upgrade))
            background = null
            layoutParams = upgradeLayoutParams()
        }
        
        sell = ImageButton(context).apply {
            id = generateViewId()
            alpha = 0.8f
            setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.sell))
            background = null
            layoutParams = sellLayoutParams()
        }
        
        
        upgradeNumber = TextView(context).apply {
            id = generateViewId()
            textSize = 18f
            setTextColor(Color.WHITE)
            text = ""
            layoutParams = upgradeNumberLayoutParams()
        }
        
        sellNumber = TextView(context).apply {
            id = generateViewId()
            textSize = 18f
            setTextColor(Color.WHITE)
            text = ""
            layoutParams = sellNumberLayoutParams()
            x = sell.x - sell.width
        }
        
        damageType = ImageButton(context).apply {
            id = generateViewId()
            alpha = 1f
            setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.first_damage))
            background = null
            layoutParams = damageTypeLayoutParams()
        }
        
        setOnclickListeners()
        addViews()
    }
    
    private fun sellLayoutParams(): LayoutParams {
        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        layoutParams.addRule(RelativeLayout.LEFT_OF, upgrade.id)
        layoutParams.marginEnd = 16
        return layoutParams
    }
    
    private fun upgradeLayoutParams(): LayoutParams {
        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        return layoutParams
    }
    
    private fun sellNumberLayoutParams(): LayoutParams {
        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        layoutParams.addRule(RelativeLayout.ABOVE, sell.id)
        layoutParams.addRule(RelativeLayout.LEFT_OF, upgrade.id)
        layoutParams.marginEnd = 16
        return layoutParams
    }

    
    private fun upgradeNumberLayoutParams(): LayoutParams {
        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        layoutParams.addRule(RelativeLayout.ABOVE, upgrade.id)
        return layoutParams
    }
    
    private fun damageTypeLayoutParams(): LayoutParams {
        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        layoutParams.addRule(RelativeLayout.RIGHT_OF, upgrade.id)
        layoutParams.marginStart = 16
        return layoutParams
    }
    
    
    private fun addViews() {
        //addView(sellNumber)
        addView(upgradeNumber)
        addView(sell)
        addView(upgrade)
        addView(damageType)
    }
    
    fun updateCostTexts() {
        if (towerClicked == null) return
        post {
            sellNumber.text = towerClicked?.buildCost().toString()
            upgradeNumber.text = towerClicked?.upgradeCost().toString()
        }
    }
    
    
    private fun setOnclickListeners() {
        sell.setOnClickListener {
            if (towerClicked != null) {
                money.addAndGet(towerClicked!!.buildCost())
                towerClicked!!.destroy()
                gameView!!.hideTowerButtons()
            }
        }
        
        upgrade.setOnClickListener {
            if (towerClicked != null) {
                if (money.addAndGet(-towerClicked!!.upgradeCost()) >= 0) towerClicked!!.upgrade()
                else money.addAndGet(towerClicked!!.upgradeCost())
            }
        }
        
        damageType.setOnClickListener {
            towerClicked?.let {
                towerClicked!!.nextToDamageType()
                damageType.setImageBitmap(towerClicked!!.getToDamageType().getBitmap())
            }
        }
        
    }
    
    fun showTowerButtons() {
        //sell.visibility = VISIBLE
        //upgrade.visibility = VISIBLE
        //damageType.visibility = VISIBLE
        damageType.setImageBitmap(towerClicked?.getToDamageType()!!.getBitmap())
    }
    
    fun hideTowerButtons() {
        //sell.visibility = INVISIBLE
        //upgrade.visibility = INVISIBLE
        //damageType.visibility = INVISIBLE
    }
}