package fr.polytech.cryptodnf.dnf

import java.lang.StringBuilder

class DNF(  private var _maxSizeConjunction: Int = 3) {
    var formula: ArrayList<Conjunction> = ArrayList()

    fun add(conjunction: Conjunction){
        formula.add(conjunction)
    }

    override fun toString(): String {
        val builder = StringBuilder()

        for (i in 0 until formula.size){
            if(i != 0)
                builder.append(Operators.OR.op)

            builder.append("(")

            builder.append(formula[i])

            builder.append(")")
        }

        return builder.toString()
    }

}