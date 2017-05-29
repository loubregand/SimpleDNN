/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.simplednn.core.neuralprocessor.feedforward

import com.kotlinnlp.simplednn.core.layers.LayerType
import com.kotlinnlp.simplednn.core.neuralnetwork.NetworkParameters
import com.kotlinnlp.simplednn.core.neuralnetwork.NeuralNetwork
import com.kotlinnlp.simplednn.core.neuralnetwork.structure.feedforward.FeedforwardNetworkStructure
import com.kotlinnlp.simplednn.core.neuralprocessor.NeuralProcessor
import com.kotlinnlp.simplednn.simplemath.ndarray.DenseNDArray
import com.kotlinnlp.simplednn.simplemath.ndarray.NDArray

/**
 *
 * @param neuralNetwork a neuralNetwork
 */
class FeedforwardNeuralProcessor<InputNDArrayType : NDArray<InputNDArrayType>>(
  override val neuralNetwork: NeuralNetwork
) : NeuralProcessor {

  /**
   *
   */
  private val inputType = this.neuralNetwork.layersConfiguration.first().inputType

  /**
   * The errors of the network model parameters
   */
  private val backwardParamsErrors: NetworkParameters = this.neuralNetwork.parametersFactory(
    sparseInput = this.neuralNetwork.layersConfiguration.first().inputType == LayerType.Input.SparseBinary)

  /**
   *
   */
  var structure = FeedforwardNetworkStructure<InputNDArrayType>(
    layersConfiguration = this.neuralNetwork.layersConfiguration,
    params = this.neuralNetwork.model)

  /**
   *
   * @return
   */
  override fun getOutput(copy: Boolean): DenseNDArray {
    return if (copy) {
      this.structure.outputLayer.outputArray.values.copy()
    } else {
      this.structure.outputLayer.outputArray.values
    }
  }

  /**
   *
   */
  override fun getParamsErrors(): NetworkParameters {

    val paramsError = this.neuralNetwork.parametersFactory(
      sparseInput = this.neuralNetwork.layersConfiguration.first().inputType == LayerType.Input.SparseBinary)

    paramsError.assignValues(this.backwardParamsErrors)

    return paramsError
  }

  /**
   *
   */
  fun getInputErrors(copy: Boolean = true): DenseNDArray {
    require(this.inputType == LayerType.Input.Dense) { "Input errors available only if input is dense" }

    return if (copy) {
      this.structure.inputLayer.inputArray.errors.copy()
    } else {
      this.structure.inputLayer.inputArray.errors
    }
  }

  /**
   *
   * @param featuresArray features
   */
  fun forward(featuresArray: InputNDArrayType, useDropout: Boolean = false): DenseNDArray {

    this.structure.forward(featuresArray, useDropout = useDropout)

    return this.structure.outputLayer.outputArray.values
  }

  /**
   *
   * @param outputErrors the errors on the output of the network
   * @param propagateToInput propagateErrorsToInput
   * @return the avgLoss respect to the output of the network
   */
  fun backward(outputErrors: DenseNDArray,
               propagateToInput: Boolean = false) {
    this.structure.backward(
      outputErrors = outputErrors,
      paramsErrors = this.backwardParamsErrors,
      propagateToInput = propagateToInput)
  }
}
