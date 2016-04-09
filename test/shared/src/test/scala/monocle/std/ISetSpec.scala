package monocle.std

import monocle.MonocleSuite
import monocle.law.discipline.function.{AtTests, EmptyTests}

import scalaz.ISet

class ISetSpec extends MonocleSuite {
  checkAll("at ISet", AtTests[ISet[Int], Int, Boolean])
  checkAll("empty ISet", EmptyTests[ISet[Int]])
}
