package com.example.paproject
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhonemeAdapter(private val phonemes: List<PhonemeItem>) :
    RecyclerView.Adapter<PhonemeAdapter.PhonemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhonemeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_phoneme, parent, false)
        return PhonemeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhonemeViewHolder, position: Int) {
        val phonemeItem = phonemes[position]
        holder.bind(phonemeItem)
    }

    override fun getItemCount(): Int {
        return phonemes.size
    }

    inner class PhonemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val phonemeTextView: TextView = itemView.findViewById(R.id.phonemeTextView)
        private val scoreTextView: TextView = itemView.findViewById(R.id.scoreTextView)

        fun bind(phonemeItem: PhonemeItem) {
            phonemeTextView.text = phonemeItem.phoneme
            scoreTextView.text = phonemeItem.score.toString()
        }
    }
}