package com.example.towerdefense

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.example.towerdefense.utility.money
import com.example.towerdefense.utility.towerClicked

class TowerMenuView(context: Context) : RelativeLayout(context) {
    private lateinit var sell: Button
    private lateinit var upgrade: Button
    private lateinit var damageType: ImageButton
    
    init {
        setupButtons(context)
        setBackgroundResource(R.drawable.universal_background_h)
    }
    
    private fun setupButtons(context: Context) {
        sell = Button(context).apply {
            id = View.generateViewId()
            text = "Sell"
            alpha = 0.8f
            layoutParams = sellLayoutParams()
        }
        
        upgrade = Button(context).apply {
            id = View.generateViewId()
            text = "Upgrade"
            alpha = 0.8f
            layoutParams = upgradeLayoutParams()
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
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START)
        layoutParams.marginStart = 0
        return layoutParams
    }
    
    private fun upgradeLayoutParams(): LayoutParams {
        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, sell.id)
        layoutParams.addRule(RelativeLayout.END_OF, sell.id)
        layoutParams.marginStart = 16
        return layoutParams
    }
    
    private fun damageTypeLayoutParams(): LayoutParams {
        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(ALIGN_BOTTOM, upgrade.id)
        layoutParams.addRule(END_OF, upgrade.id)
        layoutParams.marginStart = 16
        return layoutParams
    }
    
    private fun addViews() {
        addView(sell)
        addView(upgrade)
        addView(damageType)
    }
    
    private fun setOnclickListeners() {
        sell.setOnClickListener {
            if (towerClicked != null) {
                money.addAndGet(towerClicked!!.buildCost())
                towerClicked!!.destroy()
            }
        }
        
        upgrade.setOnClickListener {
            if (towerClicked != null) {
                if (money.getAndAdd(-towerClicked!!.upgradeCost()) >= 100) towerClicked!!.upgrade()
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