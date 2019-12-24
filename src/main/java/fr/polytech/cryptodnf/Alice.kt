package fr.polytech.cryptodnf

import fr.polytech.cryptodnf.dnf.Conjunction
import fr.polytech.cryptodnf.dnf.DNF
import java.math.BigInteger

class Alice(
) {
    private var DNF: DNF = DNF()

    init {
        //first conjunction
        val x1 = Pair(1,false)
        val notX2 = Pair(2,true)
        val x3 = Pair(3,false)
        val elements1 = ArrayList<Pair<Int, Boolean>>()
        elements1.add(x1)
        elements1.add(notX2)
        elements1.add(x3)
        val conjunction1 = Conjunction(elements1)

        //second conjunction
        val notX1 = Pair(1,true)
        val x4 = Pair(4,false)
        val elements2 = ArrayList<Pair<Int, Boolean>>()
        elements2.add(notX1)
        elements2.add(x4)
        val conjunction2 = Conjunction(elements2)

        DNF.add(conjunction1)
        DNF.add(conjunction2)

        println()
        println("Secret DNF $DNF")
    }

    fun negation() {
        throw NotImplementedError()
    }

    fun conjonction() {
        throw NotImplementedError()
    }

    fun apply(vector: Vector): Vector {

        val res = Vector(DNF.formula.size)

        for (i in 0 until DNF.formula.size){
           var temp = Bob.encryptWithPublicKey(BigInteger.ZERO)
           val conjunction = DNF.formula[i]
           for (j in 0 until conjunction.size()){
               val operand = conjunction.get(j)
               val xj = vector[operand.first-1]
               val cxj =  if(operand.second){
                   //negation
                   Bob.encryptWithPublicKey(BigInteger.ONE)*xj.modInverse(Bob.publicKey*Bob.publicKey)
               }else{
                  xj
               }
               //println("decr. x$j " + Bob.decrypt(cxj))
               temp *= cxj
           }
            //println("decr. sum$i " + Bob.decrypt(temp))
            //substract 3
            res[i] = temp*Bob.encryptWithPublicKey(BigInteger.ZERO)*(Bob.encryptWithPublicKey(BigInteger.valueOf(3))).modInverse(Bob.publicKey*Bob.publicKey)
            //println("decr. res$i " + Bob.decrypt(res[i]))
        }

        return res
    }

}