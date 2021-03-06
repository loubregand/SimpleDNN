/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.simplednn.core.functionalities.initializers

import com.kotlinnlp.simplednn.simplemath.ndarray.dense.DenseNDArray

/**
 * An initializer of dense arrays with the same value for all the elements.
 *
 * @param value the initializing value
 */
class ConstantInitializer(val value: Double) : Initializer {

  companion object {

    /**
     * Private val used to serialize the class (needed from Serializable)
     */
    @Suppress("unused")
    private const val serialVersionUID: Long = 1L
  }

  /**
   * Initialize the values of the given [array].
   *
   * @param array a dense array
   */
  override fun initialize(array: DenseNDArray) {
    array.assignValues(this.value)
  }
}
