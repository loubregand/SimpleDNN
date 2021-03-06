/* Copyright 2016-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package logicgates

import com.kotlinnlp.simplednn.dataset.SimpleExample
import com.kotlinnlp.simplednn.simplemath.ndarray.dense.DenseNDArray

fun main(args: Array<String>) {
  println("Start 'NOT Gate Test'")
  println("Accuracy (softmax): %.1f%%".format(100.0 * NOTGate.testAccuracyWithSoftmax()))
  println("Accuracy (sigmoid): %.1f%%".format(100.0 * NOTGate.testAccuracyWithSigmoid()))
  println("End.")
}

object NOTGate {

  /**
   *
   */
  fun testAccuracyWithSoftmax(): Double {

    val examples: ArrayList<SimpleExample<DenseNDArray>> = ArrayList()

    examples.addAll(listOf(
      SimpleExample(doubleArrayOf(0.0), doubleArrayOf(0.0, 1.0)),
      SimpleExample(doubleArrayOf(1.0), doubleArrayOf(1.0, 0.0))
    ))

    return GateTestUtils.testAccuracyWithSoftmax(inputSize = 1, examples = examples, epochs = 100)
  }

  /**
   *
   */
  fun testAccuracyWithSigmoid(): Double {

    val examples: ArrayList<SimpleExample<DenseNDArray>> = ArrayList()

    examples.addAll(listOf(
      SimpleExample(doubleArrayOf(0.0), doubleArrayOf(1.0)),
      SimpleExample(doubleArrayOf(1.0), doubleArrayOf(0.0))
    ))

    return GateTestUtils.testAccuracyWithSigmoid(inputSize = 1, examples = examples, epochs = 100)
  }

}
