package com.example.juliogonzales

class Brain {

    var actual_phase : Int = 0
    var max_phases : Int = 18

    companion object {
        val instance : Brain = Brain()
    }

    init {
        actual_phase = 0
        resetCounter()
    }

    fun resetCounter(){
        if(actual_phase >= max_phases){
            actual_phase = 0
        }
    }

}