/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.simplednn.core.arrays

import com.kotlinnlp.simplednn.simplemath.ndarray.NDArray

/**
 * The [AugmentedArray] extends the [ActivableArray] with the errors.
 *
 * @property size the length of the array
 */
open class AugmentedArray<NDArrayType : NDArray<NDArrayType>>(size: Int) : ActivableArray<NDArrayType>(size) {

  /**
   *
   */
  companion object {

    /**
     * [AugmentedArray] factory by values.
     *
     * @param values the initial values to assign to the [AugmentedArray]
     *
     * @return an [AugmentedArray] with the values already initialized
     */
    operator fun <T : NDArray<T>> invoke(values: T): AugmentedArray<T> {

      val array = AugmentedArray<T>(size = values.length)

      array.assignValues(values)

      return array
    }
  }

  /**
   * Contains the errors of the current values
   */
  val errors: NDArrayType get() = this._errors

  /**
   * Contains the errors of the current values
   */
  lateinit protected var _errors: NDArrayType

  /**
   * Assign errors to the array
   *
   * @param errors errors to assign to this [AugmentedArray].
   *               The errors must have the same size of the array values.
   */
  fun assignErrors(errors: NDArrayType) { this._errors.assignValues(errors) }

  /**
   * Clone this [AugmentedArray].
   *
   * @return a clone of this [AugmentedArray]
   */
  override fun clone(): AugmentedArray<NDArrayType> {

    val clonedArray = AugmentedArray<NDArrayType>(this.size)
    clonedArray._values.assignValues(this._values)

    if (this.hasActivation) {

      if (this._valuesNotActivated != this._values) {
        clonedArray._valuesNotActivated = this._valuesNotActivated!!.copy()
      }

      clonedArray.setActivation(this.activationFunction!!)
    }

    clonedArray.errors.assignValues(this._errors)

    return clonedArray
  }
}
