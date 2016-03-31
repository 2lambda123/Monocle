package monocle.law

import monocle.Traversal
import monocle.internal.IsEq

import scalaz.Id._

class TraversalLaws[S, A](traversal: Traversal[S, A]) {
  import IsEq.syntax

  def setGetAll(s: S, a: A): IsEq[List[A]] =
    traversal.getAll(traversal.set(a)(s)) <==> traversal.getAll(s).map(_ => a)

  def setIdempotent(s: S, a: A): IsEq[S] =
    traversal.set(a)(traversal.set(a)(s)) <==> traversal.set(a)(s)

  def modifyIdentity(s: S): IsEq[S] =
    traversal.modify(identity)(s) <==> s

  def modifyFId(s: S): IsEq[S] =
    traversal.modifyF[Id](id.point[A](_))(s) <==> s

  def headOption(s: S): IsEq[Option[A]] =
    traversal.headOption(s) <==> traversal.getAll(s).headOption
}