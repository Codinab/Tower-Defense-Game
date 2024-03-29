package com.example.towerdefense.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.towerdefense.R
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.utility.towerClicked

@SuppressLint("ViewConstructor")
class TowerMenuView(private val context: GameActivity) : RelativeLayout(context) {
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
        upgrade = ImageButton(context, null, 0, R.style.ButtonStyle).apply {
            id = generateViewId()
            setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.upgrade))
            layoutParams = upgradeLayoutParams()
        }
        
        sell = ImageButton(context, null, 0, R.style.ButtonStyle).apply {
            id = generateViewId()
            setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.sell))
            layoutParams = sellLayoutParams()
        }
        
        upgradeNumber = TextView(context, null, 0, R.style.TextViewStyle).apply {
            id = generateViewId()
            layoutParams = upgradeNumberLayoutParams()
        }
        
        sellNumber = TextView(context, null, 0, R.style.TextViewStyle).apply {
            id = generateViewId()
            layoutParams = sellNumberLayoutParams()
            x = sell.x - sell.width
        }
        
        damageType = ImageButton(context, null, 0, R.style.ButtonStyle).apply {
            id = generateViewId()
            setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.first_damage))
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
                context.gameView()!!.money.addAndGet(towerClicked!!.buildCost())
                towerClicked!!.destroy()
                context.gameView()!!.hideTowerButtons()
            }
        }
        
        upgrade.setOnClickListener {
            if (towerClicked != null) {
                if (context.gameView()!!.money.addAndGet(-towerClicked!!.upgradeCost()) >= 0) towerClicked!!.upgrade()
                else context.gameView()!!.money.addAndGet(towerClicked!!.upgradeCost())
            }
        }
        
        damageType.setOnClickListener {
            towerClicked?.let {
                towerClicked!!.nextToDamageType()
                damageType.setImageBitmap(towerClicked!!.getToDamageType().getBitmap(context))
            }
        }
        
    }
    
    fun showTowerButtons() {
        damageType.setImageBitmap(towerClicked?.getToDamageType()!!.getBitmap(context))
    }
    
    //Just a test function
    fun hideTowerButtons() {
        //sell.visibility = INVISIBLE
        //upgrade.visibility = INVISIBLE
        //damageType.visibility = INVISIBLE
    }
}