package Test

import fr.polytech.berger.cryptaception.Paillier
import fr.polytech.cryptodnf.Bob
import fr.polytech.cryptodnf.Vector
import fr.polytech.cryptodnf.resolveWithMultiplicationProtocol
import fr.polytech.cryptoprojet.PaillierStatic
import org.junit.Test
import java.math.BigInteger
import kotlin.random.Random
import kotlin.test.Asserter
import kotlin.test.assertEquals


class ProtocolKtTest: Asserter {

    @Test
    fun main() {
    }

    @Test
    fun testMultiplicationProtocol(){
        val paillier = Paillier.randomCryptaception()
        val pk=paillier.publicKey
        var n=3
        var XOnlyOnes= Vector(n)
        var XOnesAndaZero=Vector(n);
        for(i in 0 until XOnlyOnes.size){
            XOnlyOnes[i]= PaillierStatic.encrypt(BigInteger.ONE,pk)
            XOnesAndaZero[i]=PaillierStatic.encrypt(BigInteger.ONE,pk)
        }
        var rdmIndex= Random(n).nextInt(n)
        XOnesAndaZero[rdmIndex]=PaillierStatic.encrypt(BigInteger.ZERO,pk)


        val encMultOnlyOnes = resolveWithMultiplicationProtocol(XOnlyOnes,paillier)
        val decMultOnlyOnes= paillier.decryptToBigInteger(encMultOnlyOnes);

//        println("result of testMultiplicationProtol:"+decMultOnlyOnes.toString())
        assertEquals(decMultOnlyOnes, BigInteger.ONE)

        val encMultOneAndaZero = resolveWithMultiplicationProtocol(XOnesAndaZero,paillier)
        val decMultOneAndaZero= paillier.decryptToBigInteger(encMultOneAndaZero);

        assertEquals(BigInteger.ZERO,decMultOneAndaZero)

    }

    override fun fail(message: String?): Nothing {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}