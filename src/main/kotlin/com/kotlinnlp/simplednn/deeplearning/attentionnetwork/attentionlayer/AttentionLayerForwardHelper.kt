/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.simplednn.deeplearning.attentionnetwork.attentionlayer

import com.kotlinnlp.simplednn.core.functionalities.activations.Softmax
import com.kotlinnlp.simplednn.simplemath.ndarray.dense.DenseNDArray

/**
 * The Attention Layer forward helper.
 *
 * @property layer the Attention Layer Structure as support to perform calculations
 */
class AttentionLayerForwardHelper(private val layer: AttentionLayerStructure<*>) {

  /**
   * Perform the forward of the input sequence contained into the [layer].
   *
   *   x_i = i-th input array
   *   alpha_i = i-th value of alpha
   *   am = attention matrix
   *   cv = context vector
   *
   *   ac = am (dot) cv  // attention context
   *   alpha = softmax(ac)  // importance score
   *
   *   y = sum by { x_i * alpha_i }
   *
   * @return the output array
   */
  fun forward() {

    val contextVect: DenseNDArray = this.layer.params.contextVector.values
    val attentionContext: DenseNDArray = this.layer.attentionMatrix.values.dot(contextVect)

    this.layer.importanceScore = Softmax().f(attentionContext)

    this.calculateOutput()
  }

  /**
   * Calculate the values of the output array.
   *
   *   y = sum by { x_i * alpha_i }
   */
  private fun calculateOutput() {

    val y: DenseNDArray = this.layer.outputArray.values

    y.zeros()

    this.layer.inputSequence.forEachIndexed { i, inputArray ->
      y.assignSum(inputArray.values.prod(this.layer.importanceScore[i]))
    }
  }
}
