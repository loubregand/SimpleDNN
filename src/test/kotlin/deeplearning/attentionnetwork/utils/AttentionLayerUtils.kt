/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package deeplearning.attentionnetwork.utils

import com.kotlinnlp.simplednn.core.arrays.AugmentedArray
import com.kotlinnlp.simplednn.core.functionalities.activations.Tanh
import com.kotlinnlp.simplednn.core.functionalities.initializers.Initializer
import com.kotlinnlp.simplednn.core.layers.LayerStructureFactory
import com.kotlinnlp.simplednn.core.layers.LayerType
import com.kotlinnlp.simplednn.core.layers.feedforward.FeedforwardLayerParameters
import com.kotlinnlp.simplednn.core.layers.feedforward.FeedforwardLayerStructure
import com.kotlinnlp.simplednn.deeplearning.attentionnetwork.AttentionNetworkParameters
import com.kotlinnlp.simplednn.deeplearning.attentionnetwork.attentionlayer.AttentionLayerParameters
import com.kotlinnlp.simplednn.simplemath.ndarray.dense.DenseNDArray
import com.kotlinnlp.simplednn.simplemath.ndarray.dense.DenseNDArrayFactory

/**
 *
 */
object AttentionLayerUtils {

  /**
   *
   */
  fun buildAttentionParams(initializer: Initializer? = null): AttentionLayerParameters {

    val params = AttentionLayerParameters(attentionSize = 2, initializer = initializer)

    params.contextVector.values.assignValues(DenseNDArrayFactory.arrayOf(doubleArrayOf(-0.3, -0.5)))

    return params
  }

  /**
   *
   */
  fun buildInputSequence(): ArrayList<AugmentedArray<DenseNDArray>> = arrayListOf(
    AugmentedArray(DenseNDArrayFactory.arrayOf(doubleArrayOf(-0.4, 0.7, 0.9, 0.6))),
    AugmentedArray(DenseNDArrayFactory.arrayOf(doubleArrayOf(-0.5, 0.7, -0.7, 0.8))),
    AugmentedArray(DenseNDArrayFactory.arrayOf(doubleArrayOf(0.3, -0.5, 0.0, 0.2)))
  )

  /**
   *
   */
  fun buildAttentionSequence(inputSequence: ArrayList<AugmentedArray<DenseNDArray>>): ArrayList<DenseNDArray> {

    val transformLayer: FeedforwardLayerStructure<DenseNDArray> = buildTransformLayer()

    return arrayListOf(*Array(
      size = inputSequence.size,
      init = { i ->
        transformLayer.setInput(inputSequence[i].values)
        transformLayer.forward()
        transformLayer.outputArray.values.copy()
      }
    ))
  }

  /**
   *
   */
  fun buildOutputErrors(): DenseNDArray = DenseNDArrayFactory.arrayOf(
    doubleArrayOf(-0.2, 0.5, 0.1, -0.5)
  )

  /**
   *
   */
  fun buildTransformLayerParams1(): FeedforwardLayerParameters {

    val params = FeedforwardLayerParameters(inputSize = 4, outputSize = 2)

    params.unit.weights.values.assignValues(DenseNDArrayFactory.arrayOf(arrayOf(
      doubleArrayOf(0.3, 0.4, 0.2, -0.2),
      doubleArrayOf(0.2, -0.1, 0.1, 0.6)
    )))

    params.unit.biases.values.assignValues(DenseNDArrayFactory.arrayOf(
      doubleArrayOf(0.3, -0.4)
    ))

    return params
  }

  /**
   *
   */
  fun buildTransformLayerParams2(): FeedforwardLayerParameters {

    val params = FeedforwardLayerParameters(inputSize = 4, outputSize = 2)

    params.unit.weights.values.assignValues(DenseNDArrayFactory.arrayOf(arrayOf(
      doubleArrayOf(0.7, -0.8, 0.1, -0.6),
      doubleArrayOf(0.8, 0.6, -0.9, -0.2)
    )))

    params.unit.biases.values.assignValues(DenseNDArrayFactory.arrayOf(
      doubleArrayOf(-0.9, 0.1)
    ))

    return params
  }

  /**
   *
   */
  fun buildAttentionNetworkParams1(): AttentionNetworkParameters {

    val params = AttentionNetworkParameters(inputSize = 4, attentionSize = 2, sparseInput = false)
    val transformParams = buildTransformLayerParams1()
    val attentionParams = buildAttentionParams() // [-0.3, -0.5]

    params.transformParams.unit.weights.values.assignValues(transformParams.unit.weights.values)
    params.transformParams.unit.biases.values.assignValues(transformParams.unit.biases.values)
    params.attentionParams.contextVector.values.assignValues(attentionParams.contextVector.values)

    return params
  }

  /**
   *
   */
  fun buildAttentionNetworkParams2(): AttentionNetworkParameters {

    val params = AttentionNetworkParameters(inputSize = 4, attentionSize = 2, sparseInput = false)
    val transformParams = buildTransformLayerParams2()

    params.transformParams.unit.weights.values.assignValues(transformParams.unit.weights.values)
    params.transformParams.unit.biases.values.assignValues(transformParams.unit.biases.values)
    params.attentionParams.contextVector.values.assignValues(
      DenseNDArrayFactory.arrayOf(doubleArrayOf(-0.1, 0.4))
    )

    return params
  }

  /**
   *
   */
  private fun buildTransformLayer(): FeedforwardLayerStructure<DenseNDArray> {

    return LayerStructureFactory(
      inputArray = AugmentedArray<DenseNDArray>(size = 4),
      outputSize = 2,
      params = this.buildTransformLayerParams1(),
      activationFunction = Tanh(),
      connectionType = LayerType.Connection.Feedforward
    ) as FeedforwardLayerStructure
  }
}
