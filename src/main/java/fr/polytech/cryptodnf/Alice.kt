package fr.polytech.cryptodnf

import fr.polytech.cryptodnf.dnf.Conjunction
import fr.polytech.cryptodnf.dnf.DNF
import java.math.BigInteger
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt

class Alice {
    private var DNF: DNF = DNF()
    private var SIZE_TO_FILL = 15

    init {
        //first conjunction
        val x1 = Pair(1, false)
        val notX2 = Pair(2, true)
        val x3 = Pair(3, false)
        val elements1 = ArrayList<Pair<Int, Boolean>>()
        elements1.add(x1)
        elements1.add(notX2)
        elements1.add(x3)
        val conjunction1 = Conjunction(elements1)

        //second conjunction
        val notX1 = Pair(1, true)
        val x4 = Pair(4, false)
        val elements2 = ArrayList<Pair<Int, Boolean>>()
        elements2.add(notX1)
        elements2.add(x4)
        val conjunction2 = Conjunction(elements2)

        DNF.add(conjunction1)
        DNF.add(conjunction2)

        println()
        println("Secret DNF $DNF")
    }

    fun apply(vector: Vector): Vector {

        val res = Vector(DNF.formula.size)

        for (i in 0 until DNF.formula.size) {
            var temp = Bob.encryptWithPublicKey(BigInteger.ZERO)
            val conjunction = DNF.formula[i]
            for (j in 0 until conjunction.size()) {
                val operand = conjunction.get(j)
                val xj = vector[operand.first - 1]

                // Negative if necessary
                val cxj = if (operand.second) {
                    Bob.encryptWithPublicKey(BigInteger.ONE) * xj.modInverse(Bob.publicKey * Bob.publicKey)
                } else {
                    xj
                }
//                println("decr. x"+(j+1) + " "+ Bob.decrypt(cxj))
                temp *= cxj
            }
//            println("decr. sum$i " + Bob.decrypt(temp))

            // Subtract 3
            res[i] = temp * Bob.encryptWithPublicKey(BigInteger.valueOf(conjunction.size().toLong())).modInverse(Bob.publicKey*Bob.publicKey)
//            println("decr. res$i " + Bob.decrypt(res[i]))

//             Multiply by random value
            val randomValue = nextRandomBigInteger(Bob.publicKey)

            res[i] = res[i].modPow(randomValue, Bob.publicKey*Bob.publicKey)
//            println("decr. res$i " + Bob.decrypt(res[i]))
        }

        return res
    }

    fun dnf(vector: Vector): Vector{
        return randomize(fill(apply(vector)))
    }

    fun fill(vector: Vector): Vector {
        val filledVector = Vector(SIZE_TO_FILL)


        for (i in 0 until vector.size){
            filledVector[i] = vector[i]
        }

        for (j in vector.size until SIZE_TO_FILL){
            val randomValue = nextRandomBigInteger(Bob.publicKey)
            filledVector[j] = Bob.encryptWithPublicKey(randomValue)
        }

        return filledVector
    }

    fun randomize(vector: Vector): Vector {
        val rand = vector.shuffle()
        return rand
    }

    fun nextRandomBigInteger(n: BigInteger): BigInteger {
        val rand = Random()
        var result = BigInteger(n.bitLength(), rand)

        //not null
        while (result.equals(BigInteger.ZERO)) {
            result = BigInteger(n.bitLength(), rand)
        }
        return result
    }

}