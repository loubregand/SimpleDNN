/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package layers.parameters

import com.kotlinnlp.simplednn.core.functionalities.randomgenerators.RandomGenerator
import com.kotlinnlp.simplednn.core.layers.feedforward.FeedforwardLayerParameters
import com.kotlinnlp.simplednn.simplemath.ndarray.dense.DenseNDArray
import com.kotlinnlp.simplednn.simplemath.ndarray.sparse.SparseNDArray
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 *
 */
class FeedforwardLayerParametersSpec : Spek({

  describe("a FeedforwardNetworkParameters") {

    context("initialization") {

      on("dense input") {

        val params = FeedforwardLayerParameters(inputSize = 3, outputSize = 2)

        val w = params.unit.weights.values
        val b = params.unit.biases.values

        var i = 0
        val initValues = doubleArrayOf(0.1, 0.2, 0.3, 0.4, 0.5, 0.6)
        val randomGenerator = mock<RandomGenerator>()
        whenever(randomGenerator.next()).thenAnswer { initValues[i++] }

        params.initialize(randomGenerator = randomGenerator, biasesInitValue = 0.9)

        it("should contain dense weights") {
            assertTrue { w is DenseNDArray }
        }

        it("should contain dense biases") {
            assertTrue { b is DenseNDArray }
        }

        it("should contain the expected initialized weights") {
          (0 until w.length).forEach { i -> assertEquals(initValues[i], w[i]) }
        }

        it("should contain the expected initialized biases") {
          (0 until b.length).forEach { i -> assertEquals(0.9, b[i]) }
        }
      }

      on("sparse input") {

        val params = FeedforwardLayerParameters(inputSize = 3, outputSize = 2, sparseInput = true)

        val w = params.unit.weights.values
        val b = params.unit.biases.values

        it("should contain sparse weights") {
            assertTrue { w is SparseNDArray }
        }

        it("should contain dense biases") {
            assertTrue { b is DenseNDArray }
        }

        it("should throw an Exception when trying to initialize") {
            assertFails { params.initialize() }
        }
      }
    }

    context("iteration") {

      val params = FeedforwardLayerParameters(inputSize = 3, outputSize = 2)

      val iterator = params.iterator()

      val w = params.unit.weights
      val b = params.unit.biases

      on("iteration 1") {
        it("should return the weights") {
          assertEquals(w, iterator.next())
        }
      }

      on("iteration 2") {
        it("should return the biases") {
          assertEquals(b, iterator.next())
        }
      }

      on("iteration 3") {
        it("should return true when calling hasNext()") {
          assertFalse(iterator.hasNext())
        }
      }
    }
  }
})
