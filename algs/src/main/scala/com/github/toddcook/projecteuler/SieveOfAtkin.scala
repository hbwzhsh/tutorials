/*
 * Copyright (c) 2011, Todd Cook.
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification,
 *  are permitted provided that the following conditions are met:
 *
 *      * Redistributions of source code must retain the above copyright notice,
 *        this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright notice,
 *        this list of conditions and the following disclaimer in the documentation
 *        and/or other materials provided with the distribution.
 *      * Neither the name of the <ORGANIZATION> nor the names of its contributors
 *        may be used to endorse or promote products derived from this software
 *        without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 *  FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.toddcook.projecteuler

/**
 *  Problem 10
 *  The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
 *  Find the sum of all the primes below two million.
 *
 *  Solution:
 *  Sieve of Eratosthenes
 *  see: http://en.wikipedia.org/wiki/Sieve_of_eratosthenes
 *  1. Create a list of consecutive integers from two to n: (2, 3, 4, ..., n).
 *  2. Initially, let p equal 2, the first prime number.
 *   3. Strike from the list all multiples of p less than or equal to n.
 *   4. Find the first number remaining on the list greater than p
 *   (this number is the next prime); replace p with this number.
 *   5. Repeat steps 3 and 4 until p2 is greater than n.
 *   6. All the remaining numbers on the list are prime.
 *
 *  Or a better more modern version is the Sieve of SieveOfAtkin
 *  http://en.wikipedia.org/wiki/Sieve_of_Atkin
 *
 * @author : Todd Cook
 * @since : Oct 11, 2009
 */

import scala.collection.mutable.ListBuffer

class SieveOfAtkin (val limit: Int) {
  private var primes = new ListBuffer[Int]();
  findPrimes()

  def getPrimes (): List[Int] = primes.toList

  private def exclusiveOR (state: Boolean, shiftVal: Boolean): Boolean = {
    if (!shiftVal) {
      return state
    }
    if (state && shiftVal) {
      return false
    }
    if (!state && shiftVal) {
      return true
    }
    return state
  }

  private def findPrimes () {
    // the sieve is initialized to false
    val isPrime: Array[Boolean] = new Array[Boolean](limit + 1);
    val sqrt = (math.sqrt(limit)).round.intValue

    /**
     * put in candidate primes:
     * integers which have an odd number of
     * representations by certain quadratic forms
     */
    (1 to sqrt).foreach(x => {
      (1 to sqrt).foreach(y => {
        var n = (4 * x * x) + (y * y)
        if (n <= limit && (n % 12 == 1 || n % 12 == 5)) {
          isPrime(n) = exclusiveOR(isPrime(n), true)
        }
        n = (3 * x * x) + (y * y)
        if (n <= limit && (n % 12 == 7)) {
          isPrime(n) = exclusiveOR(isPrime(n), true)
        }
        n = (3 * x * x) - (y * y)
        if ((x > y) && (n <= limit) && (n % 12 == 11)) {
          isPrime(n) = exclusiveOR(isPrime(n), true)
        }
      })
    })
    // eliminate composites by sieving
    (5 to sqrt).foreach(n => {
      if (isPrime(n)) {
        var primeSquared = n * n
        var inc = 1
        var primeSquaredInc = primeSquared * inc
        while (primeSquaredInc <= limit) {
          // n is prime, omit multiples of its square; this is
          // sufficient because composites which managed to get
          // on the list cannot be square-free
          isPrime(primeSquaredInc) = false;
          inc += 1
          primeSquaredInc = primeSquared * inc
        }
      }
    })
    primes.append(2)
    primes.append(3)
    (5 to (limit - 1)).foreach(n => {
      if (isPrime(n)) {
        primes.append(n)
      }
    })
  }
}

object SieveOfAtkin {

  /**
   *  The simplest primality test is as follows: Given an input number n,
   *  check whether any integer m from 2 to n - 1 divides n.
   *  If n is divisible by any m then n is composite, otherwise it is prime.
   *
   *  However, rather than testing all m up to n - 1,
   *  it is only necessary to test m up to sqrt n:
   *  if n is composite then it can be factored into two values,
   *  at least one of which must be less than or equal to sqrt n.
   *
   *  The efficiency can also be improved by skipping all even m except 2,
   *  since if any even number divides n then 2 does.
   *
   *   from: http://en.wikipedia.org/wiki/Primality_test
   */
  def isPrime (n: Int): Boolean = {
    if (n == 2 || n == 3) {
      return true
    }
    if (n % 2 == 0 || n % 3 == 0) {
      return false
    }
    val ceil = math.sqrt(n).round.intValue
    var inc = 5
    while (inc <= ceil) {
      if (n % inc == 0) {
        return false
      }
      inc += 2
    }
    true
  }

  def main (args: Array[String]) {
    var soa = new SieveOfAtkin(2000000)
    soa.getPrimes.foreach(a => if (!isPrime(a)) {
      println("prime fail for: " + a)
    })
    println("total primes to 2,000,000: " + soa.getPrimes().length)
    //     var soa = new SieveOfAtkin (1000)
    //   println(soa.getPrimes.toList.mkString(", "))
  }
}