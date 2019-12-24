package fr.polytech.cryptodnf.dnf

class Conjunction(indicesArray: ArrayList<Pair<Int, Boolean>>){
    private var conjunction: ArrayList<Operand> = ArrayList()

    /**
     * Create conjunction with an array of indices of the elements implicated
     */
    init {
        for (i in 0 until indicesArray.size) {
            val pair = indicesArray[i]
            conjunction.add(Operand(pair))
        }
    }

    fun get(i : Int) : Pair<Int, Boolean>{
        return conjunction[i].op
    }

    fun size() : Int{
        return conjunction.size
    }

    override fun toString(): String {
        return conjunction.map { op -> op }.joinToString(" " + Operators.AND.op + " ")
    }

}