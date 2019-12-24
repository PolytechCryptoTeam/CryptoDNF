package fr.polytech.cryptodnf

fun main(args : Array<String>) {
    val bob = Bob()
    val alice = Alice()

    bob.encryptX()

    val res : Vector = alice.apply(bob.X)
    println(bob.multiply(res))

}