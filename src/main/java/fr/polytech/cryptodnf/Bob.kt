package fr.polytech.cryptodnf

import fr.polytech.berger.cryptaception.Paillier
import java.math.BigInteger
import kotlin.random.Random

class Bob{

    companion object{
        private var paillier = Paillier.randomCryptaception()
        var publicKey = paillier.publicKey

        fun encryptWithPublicKey(value: BigInteger): BigInteger {
            return paillier.encrypt(value)
        }

        fun decrypt(value: BigInteger): BigInteger {
            return paillier.decryptToBigInteger(value)
        }
    }

    private var n: Int = 10
    private var x: Vector = Vector(n)
    var X: Vector = Vector(n)

    init {
        val randomBits = List(n) { Random.nextInt(0, 2) }
        val randomBitsBigInteger = randomBits.map { bit -> BigInteger.valueOf(bit.toLong()) }
        for (i in 0 until n) {
            x[i] = randomBitsBigInteger[i]
        }

        print("Secret message $x")
    }

    fun encryptX(): Vector {
        for (i in 0 until x.size) {
            X[i] = paillier.encrypt(x[i])
        }
        return X
    }

    fun multiply(res: Vector): BigInteger{
//        println(res.map { vec -> paillier.decryptToBigInteger(vec)}.joinToString(" , "))
        var temp : BigInteger = BigInteger.ONE
        for (i in 0 until res.size){
            temp *= paillier.decryptToBigInteger(res[i])
        }
        return temp
    }

}