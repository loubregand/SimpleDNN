/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.simplednn.core.neuralprocessor.recurrent

import com.kotlinnlp.simplednn.core.neuralnetwork.NeuralNetwork
import com.kotlinnlp.simplednn.core.neuralprocessor.NeuralProcessor
import com.kotlinnlp.simplednn.simplemath.ndarray.NDArray
import com.kotlinnlp.simplednn.utils.ItemsPool

/**
 * A pool of [NeuralProcessor]s which allows to allocate and release processors when needed, without creating a new one.
 * It is useful to optimize the creation of new structures every time a processor is created.
 *
 * @property neuralNetwork the [NeuralNetwork] which the processors of the pool will work with
 */
class RecurrentNeuralProcessorsPool<InputNDArrayType : NDArray<InputNDArrayType>>(
  val neuralNetwork: NeuralNetwork
) : ItemsPool<RecurrentNeuralProcessor<InputNDArrayType>>() {

  /**
   * The factory of a new processor
   *
   * @param id the id of the processor to create
   *
   * @return a new [RecurrentNeuralProcessor] with the given [id]
   */
  override fun itemFactory(id: Int) = RecurrentNeuralProcessor<InputNDArrayType>(
    neuralNetwork = this.neuralNetwork,
    id = id
  )
}
