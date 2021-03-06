/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.simplednn.core.functionalities.activations

/**
 * The sigmoid activation function σ(x) = 1/(1 + e−x) is an S-shaped function,
 * transforming each value x into the range [0, 1].
 */
class Sigmoid : ScalarActivationFunction() {

  /**
   * Apply the Sigmoid function to [x].
   *
   * @param x input
   *
   * @return f([x])
   */
  override fun f(x: Double): Double = 1.0 / (1.0 + Math.exp(-x))

  /**
   * Optimized derivative of the Sigmoid function, calculated in x
   *
   * @param fx input (WARNING: it must be f(x) for optimization)
   *
   * @return the Sigmoid derivative calculated in x
   */
  override fun dfOptimized(fx: Double): Double = fx * (1.0 - fx)

}
