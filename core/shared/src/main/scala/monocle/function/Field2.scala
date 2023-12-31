package monocle.function

import monocle.{Iso, Lens}

import scala.annotation.implicitNotFound

/** Typeclass that defines a [[Lens]] from an `S` to its second element of type `A`
  * @tparam S
  *   source of [[Lens]]
  * @tparam A
  *   target of [[Lens]], `A` is supposed to be unique for a given `S`
  */
@implicitNotFound(
  "Could not find an instance of Field2[${S},${A}], please check Monocle instance location policy to " + "find out which import is necessary"
)
@deprecated("use Focus[$TupleType](_._2)", since = "3.0.0-M2")
abstract class Field2[S, A] extends Serializable {
  def second: Lens[S, A]
}

trait Field2Functions {
  @deprecated("use Focus[$TupleType](_._2)", since = "3.0.0-M2")
  def second[S, A](implicit ev: Field2[S, A]): Lens[S, A] = ev.second
}

object Field2 extends Field2Functions {
  def apply[S, A](lens: Lens[S, A]): Field2[S, A] =
    new Field2[S, A] {
      override val second: Lens[S, A] = lens
    }

  /** lift an instance of [[Field2]] using an [[Iso]] */
  def fromIso[S, A, B](iso: Iso[S, A])(implicit ev: Field2[A, B]): Field2[S, B] =
    Field2(iso.andThen(ev.second))

  /** *********************************************************************************************
    */
  /** std instances */
  /** *********************************************************************************************
    */
  implicit def tuple2Field2[A1, A2]: Field2[(A1, A2), A2] =
    Field2(
      Lens((_: (A1, A2))._2)(a => t => t.copy(_2 = a))
    )

  implicit def tuple3Field2[A1, A2, A3]: Field2[(A1, A2, A3), A2] =
    Field2(
      Lens((_: (A1, A2, A3))._2)(a => t => t.copy(_2 = a))
    )

  implicit def tuple4Field2[A1, A2, A3, A4]: Field2[(A1, A2, A3, A4), A2] =
    Field2(
      Lens((_: (A1, A2, A3, A4))._2)(a => t => t.copy(_2 = a))
    )

  implicit def tuple5Field2[A1, A2, A3, A4, A5]: Field2[(A1, A2, A3, A4, A5), A2] =
    Field2(
      Lens((_: (A1, A2, A3, A4, A5))._2)(a => t => t.copy(_2 = a))
    )

  implicit def tuple6Field2[A1, A2, A3, A4, A5, A6]: Field2[(A1, A2, A3, A4, A5, A6), A2] =
    Field2(
      Lens((_: (A1, A2, A3, A4, A5, A6))._2)(a => t => t.copy(_2 = a))
    )
}
