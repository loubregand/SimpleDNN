/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.simplednn.core.neuralnetwork.preset

import com.kotlinnlp.simplednn.core.functionalities.activations.ActivationFunction
import com.kotlinnlp.simplednn.core.functionalities.initializers.GlorotInitializer
import com.kotlinnlp.simplednn.core.functionalities.initializers.Initializer
import com.kotlinnlp.simplednn.core.layers.LayerConfiguration
import com.kotlinnlp.simplednn.core.layers.LayerType
import com.kotlinnlp.simplednn.core.neuralnetwork.NeuralNetwork

/**
 * The Delta Recurrent Neural Network factory.
 */
object DeltaRNN {

  operator fun invoke(inputSize: Int,
                      hiddenSize: Int,
                      hiddenActivation: ActivationFunction?,
                      outputSize: Int,
                      outputActivation: ActivationFunction?,
                      inputType: LayerType.Input = LayerType.Input.Dense,
                      inputDropout: Double = 0.0,
                      hiddenDropout: Double = 0.0,
                      weightsInitializer: Initializer? = GlorotInitializer(),
                      biasesInitializer: Initializer? = GlorotInitializer()) = NeuralNetwork(
    LayerConfiguration(
      size = inputSize,
      inputType = inputType,
      dropout = inputDropout
    ),
    LayerConfiguration(
      size = hiddenSize,
      activationFunction = hiddenActivation,
      connectionType = LayerType.Connection.DeltaRNN,
      dropout = hiddenDropout
    ),
    LayerConfiguration(
      size = outputSize,
      activationFunction = outputActivation,
      connectionType = LayerType.Connection.Feedforward
    ),
    weightsInitializer = weightsInitializer,
    biasesInitializer = biasesInitializer
  )
}
