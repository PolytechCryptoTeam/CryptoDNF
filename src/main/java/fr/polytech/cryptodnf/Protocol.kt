package fr.polytech.cryptodnf

import java.math.BigInteger

fun main(args : Array<String>) {
    val bob = Bob()
    val alice = Alice()

    bob.encryptX()

    val conj : Vector = alice.dnf(bob.X)
    val res = bob.multiply(conj)
    println("Result " + (res === BigInteger.ZERO))
}