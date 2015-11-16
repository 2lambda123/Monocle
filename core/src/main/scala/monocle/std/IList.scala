package monocle.std

import monocle.function._
import monocle.{Iso, Optional, PIso, Prism, Traversal}

import scalaz.Id.Id
import scalaz.std.option._
import scalaz.syntax.traverse._
import scalaz.{Applicative, ICons, IList, INil}

object ilist extends IListInstances

trait IListInstances {

  final def pIListToList[A, B]: PIso[IList[A], IList[B], List[A], List[B]] =
    PIso[IList[A], IList[B], List[A], List[B]](_.toList)(IList.fromList)

  final def iListToList[A]: Iso[IList[A], List[A]] =
    pIListToList[A, A]

  implicit def iListEmpty[A]: Empty[IList[A]] = new Empty[IList[A]] {
    def empty = Prism[IList[A], Unit](l => if(l.isEmpty) Some(()) else None)(_ => IList.empty)
  }

  implicit def iNilEmpty[A]: Empty[INil[A]] = new Empty[INil[A]] {
    def empty = Prism[INil[A], Unit](_ => Some(()))(_ => INil())
  }

  implicit def iListEach[A]: Each[IList[A], A] = Each.traverseEach[IList, A]

  implicit def iListIndex[A]: Index[IList[A], Int, A] = new Index[IList[A], Int, A] {
    def index(i: Int) = Optional[IList[A], A](
      il      => if(i < 0) None else il.drop(i).headOption)(
      a => il => il.zipWithIndex.traverse[Id, A]{
        case (_    , index) if index == i => a
        case (value, index)               => value
      }
    )
  }

  implicit def iListFilterIndex[A]: FilterIndex[IList[A], Int, A] =
    FilterIndex.traverseFilterIndex[IList, A](_.zipWithIndex)

  implicit def iListCons[A]: Cons[IList[A], A] = new Cons[IList[A], A]{
    def cons = Prism[IList[A], (A, IList[A])]{
      case INil()       => None
      case ICons(x, xs) => Some((x, xs))
    }{ case (a, s) => ICons(a, s) }
  }

  implicit def iListSnoc[A]: Snoc[IList[A], A] = new Snoc[IList[A], A]{
    def snoc = Prism[IList[A], (IList[A], A)](
      il => Applicative[Option].apply2(il.initOption, il.lastOption)((_,_))){
      case (init, last) => init :+ last
    }
  }

  implicit def iListReverse[A]: Reverse[IList[A], IList[A]] =
    Reverse.reverseFromReverseFunction[IList[A]](_.reverse)

  implicit def ilistPlated[A]: Plated[IList[A]] = new Plated[IList[A]] {
    val plate: Traversal[IList[A], IList[A]] = new Traversal[IList[A], IList[A]] {
      def modifyF[F[_]: Applicative](f: IList[A] => F[IList[A]])(s: IList[A]): F[IList[A]] =
        s match {
          case ICons(x, xs) => Applicative[F].map(f(xs))(x :: _)
          case INil() => Applicative[F].point(INil())
        }
    }
  }

}
