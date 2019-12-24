package fr.polytech.cryptodnf.dnf

class Operand (pair : Pair<Int, Boolean> ){

    var op: Pair<Int, Boolean> = pair

    override fun toString(): String {
        if(op.second)
            return Operators.NOT.op + op.first
        return op.first.toString()
    }

}