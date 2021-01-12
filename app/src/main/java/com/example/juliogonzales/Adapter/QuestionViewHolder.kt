package com.example.juliogonzales.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.juliogonzales.Models.Question
import com.example.juliogonzales.R

class QuestionViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_question, parent, false)) {
    private var tvInstruction: TextView? = null
    private var tvInstructionDesc: TextView? = null
    private var tvQuestionNum: TextView? = null


    init {
        tvInstruction = itemView.findViewById(R.id.tvInstruction)
        tvInstructionDesc = itemView.findViewById(R.id.tvInstructionDesc)
        tvQuestionNum = itemView.findViewById(R.id.tvQuestionNum)
    }

    fun bind(questionModel: Question) {
        tvInstruction?.text = questionModel.instruction
        tvInstructionDesc?.text = questionModel.instructionDescription
        var positionF = position + 1
        tvQuestionNum?.text = "$positionF/19"
    }



}

class ListAdapter(private val list: List<Question>)
    : RecyclerView.Adapter<QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return QuestionViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question: Question = list[position]

        holder.bind(question)
    }

    override fun getItemCount(): Int = list.size



}