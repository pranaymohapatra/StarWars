package com.starwars.starwarstournament.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.starwars.starwarstournament.R
import com.starwars.starwarstournament.common.dto.matchdetails.MatchItem
import com.starwars.starwarstournament.databinding.ItemMatchDetailsBinding

class MatchDetailsAdapter : ListAdapter<MatchItem, MatchDetailsViewHolder>(matchDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchDetailsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_match_details, parent, false)
        return MatchDetailsViewHolder(ItemMatchDetailsBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: MatchDetailsViewHolder, position: Int) {
        holder.apply {
            itemPos = position
            bind(getItem(position))
        }
    }

}

class MatchDetailsViewHolder(private val viewBinding: ItemMatchDetailsBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var itemPos: Int = -1
    fun bind(item: MatchItem) {
        viewBinding.apply {
            player1Tv.text = item.player1Name
            player1PointsTv.text = item.player1Score.toString()
            player2Tv.text = item.player2Name
            player2PointsTv.text = item.player2Score.toString()
        }
    }
}

val matchDiffUtil = object : DiffUtil.ItemCallback<MatchItem>() {
    override fun areItemsTheSame(oldItem: MatchItem, newItem: MatchItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MatchItem, newItem: MatchItem) =
        oldItem == newItem
}
