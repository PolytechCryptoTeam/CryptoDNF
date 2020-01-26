package fr.polytech.cryptodnf

import fr.polytech.berger.cryptaception.Paillier
import fr.polytech.cryptoprojet.ProtocoleMultiplication
import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main(args : Array<String>) {
    val bob = Bob()
    val alice = Alice()

    bob.encryptX()

    val conj : Vector = alice.dnf(bob.X)

    // With multiply protocol
    val executionTimeMultiplyProtocol = measureTimeMillis {
        val encryptedMult = resolveWithMultiplicationProtocol(conj, Bob.paillier)
        val decryptedMult = Bob.decrypt(encryptedMult)
        println("\nResult with multiply protocol = " + (decryptedMult === BigInteger.ZERO))
    }
    println("Execution time (multiplication protocol) : $executionTimeMultiplyProtocol ms")

    // Without multiply protocol
    val executionTimeWithoutMultiplyProtocol = measureTimeMillis {
        val decryptedWithoutMult = bob.multiply(conj)
        println("\nResult without multiply protocol = " + (decryptedWithoutMult === BigInteger.ZERO))
    }
    println("Execution time (without multiplication protocol) : $executionTimeWithoutMultiplyProtocol ms")
}

// we apply the multiplication protocol to resolve the DNF
// we multiply each clause with each other
// since they are all the encryption of either 1 or 0
// If one of the clause is 0 then the result will be 0
// Hence it is a correct AND statement
// return: encryption of AND statement
fun resolveWithMultiplicationProtocol(conj: Vector, paillier: Paillier): BigInteger {
    // cf
    val protocoleMultiplication = ProtocoleMultiplication(paillier)
    var encryptedMult = conj[0]
    for (i in 1 until conj.size) {
        encryptedMult = protocoleMultiplication.secureMultiplication(encryptedMult, conj[i])
    }
    return encryptedMult
}