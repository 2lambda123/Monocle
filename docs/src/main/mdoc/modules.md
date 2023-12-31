---
id: modules
title: Modules
---

In an attempt to be modular, Monocle is broken up into several modules:

* *core* - contains optics (e.g. `Lens`, `Prism`, `Traversal`) and type class definitions (e.g. `Index`, `Each`, `Plated`) and
  type class instances for standard library types and cats data types
* *macro* - macros to simplify the generation of optics
* *laws* - laws for the optics and type classes
* *refined* - optics and type class instances using refinement types from [refined](https://github.com/fthomas/refined)
* *generic* (deprecated) - optics and type class instances for `HList` and `Coproduct` from [shapeless](https://github.com/milessabin/shapeless)
* *state* (deprecated) - conversion between optics and `State` or `Reader`
* *unsafe* (deprecated) - optics that do not fully satisfy laws but that are very convenient. More details [here](unsafe_module.html)
* *tests* - tests that check optics and type class instances satisfy laws
* *bench* - benchmarks using jmh to measure optics performances
* *docs* - source for this website

You can add a module to your build by adding the following line to `libraryDependencies`:

```scala
"dev.optics"  %%  "monocle-${module}" % ${version}
```

Here is the complete list of published artifacts:

```scala
libraryDependencies ++= Seq(
  "dev.optics"  %%  "monocle-core"    % ${version},
  "dev.optics"  %%  "monocle-generic" % ${version},
  "dev.optics"  %%  "monocle-macro"   % ${version},
  "dev.optics"  %%  "monocle-state"   % ${version},
  "dev.optics"  %%  "monocle-refined" % ${version},
  "dev.optics"  %%  "monocle-unsafe"  % ${version},
  "dev.optics"  %%  "monocle-law"     % ${version} % "test"
)
```

You need to replace `${version}` with the version of Monocle you want to use.
