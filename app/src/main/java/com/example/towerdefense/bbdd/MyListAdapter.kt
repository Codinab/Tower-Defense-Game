package com.example.towerdefense.bbdd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.towerdefense.bbdd.tablesClasses.GameInfo
import androidx.recyclerview.widget.ListAdapter
import com.example.towerdefense.R


class MyListAdapter : ListAdapter<GameInfo, MyListAdapter.GameInfoViewHolder>(GameInfoComparator()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameInfoViewHolder {
        return GameInfoViewHolder.create(parent)
    }
    
    override fun onBindViewHolder(holder: GameInfoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }
    
    class GameInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewGameInfo: TextView = itemView.findViewById(R.id.levelTextView)
        
       
        
        fun bind(game: GameInfo) {
            
            textViewGameInfo.text = "Name: ${game.name} Score: ${game.score}"
           
            textViewGameInfo.background
        }
        
        companion object {
            fun create(parent: ViewGroup): GameInfoViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return GameInfoViewHolder(view)
            }
        }
    }
    
    class GameInfoComparator : DiffUtil.ItemCallback<GameInfo>() {
        override fun areItemsTheSame(oldItem: GameInfo, newItem: GameInfo): Boolean {
            return oldItem === newItem
        }
        
        override fun areContentsTheSame(oldItem: GameInfo, newItem: GameInfo): Boolean {
            return oldItem.name == newItem.name
        }
    }
}