/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package deeplearning.mergelayers.affine

import com.kotlinnlp.simplednn.core.arrays.AugmentedArray
import com.kotlinnlp.simplednn.core.functionalities.activations.Tanh
import com.kotlinnlp.simplednn.deeplearning.mergelayers.affine.AffineLayerParameters
import com.kotlinnlp.simplednn.deeplearning.mergelayers.affine.AffineLayerStructure
import com.kotlinnlp.simplednn.simplemath.ndarray.dense.DenseNDArray
import com.kotlinnlp.simplednn.simplemath.ndarray.dense.DenseNDArrayFactory

/**
 *
 */
object AffineLayerUtils {

  /**
   *
   */
  fun buildLayer(): AffineLayerStructure<DenseNDArray> = AffineLayerStructure(
    inputArray1 = AugmentedArray(values = DenseNDArrayFactory.arrayOf(doubleArrayOf(-0.8, -0.9))),
    inputArray2 = AugmentedArray(values = DenseNDArrayFactory.arrayOf(doubleArrayOf(0.5, -0.2, 0.6))),
    outputArray = AugmentedArray(size = 2),
    params = buildParams(),
    activationFunction = Tanh()
  )

  /**
   *
   */
  fun buildParams(): AffineLayerParameters {

    val params = AffineLayerParameters(inputSize1 = 2, inputSize2 = 3, outputSize = 2)

    params.w1.values.assignValues(
      DenseNDArrayFactory.arrayOf(arrayOf(
        doubleArrayOf(0.3, 0.8),
        doubleArrayOf(0.8, -0.7)
      )))

    params.w2.values.assignValues(
      DenseNDArrayFactory.arrayOf(arrayOf(
        doubleArrayOf(0.6, 0.5, -0.9),
        doubleArrayOf(0.3, -0.3, 0.3)
      )))

    params.b.values.assignValues(
      DenseNDArrayFactory.arrayOf(doubleArrayOf(0.5, -0.4)))

    return params
  }

  /**
   *
   */
  fun getOutputGold() = DenseNDArrayFactory.arrayOf(doubleArrayOf(0.1, -0.3))
}
