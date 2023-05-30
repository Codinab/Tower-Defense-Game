package com.example.towerdefense.bbdd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.towerdefense.bbdd.TablesClasses.GameInfo
import com.example.towerdefense.R
import androidx.recyclerview.widget.ListAdapter


class MyListAdapter : ListAdapter<GameInfo, MyListAdapter.GameInfoViewHolder>(GameInfoComparator()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameInfoViewHolder {
        return GameInfoViewHolder.create(parent)
    }
    
    override fun onBindViewHolder(holder: GameInfoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name, current.score)
    }
    
    class GameInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewGameInfo: TextView = itemView.findViewById(R.id.textViewGameInfo)
        private val textViewGameScore: TextView = itemView.findViewById(R.id.textViewGameScore)
        
        fun bind(text: String?, score: Int?) {
            textViewGameInfo.text = text
            textViewGameScore.text = "Score: $score"
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