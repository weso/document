---
id: readme
title: Readme
---
# Document library

The document library contains code that was developed as part of the Scala standard library but was later deprecated.

We maintain the code mainly because we used it in one of our projects and also as a small and simple test module that we can use to check continuous integration, documentation, cross-scala compilations, tests, and so on.

It consists os a basic pretty-printing library, which was based on Lindig's strict version of Wadler's adaptation of Hughes' pretty-printer derived from 
 [Document.scala](https://github.com/scala/scala/blob/v2.11.8/src/library/scala/text/Document.scala) whose author was Michel Schinz.

You can see the [web page](https://weso.github.io/document) for technical documentation.

## Further info

* [The design of a pretty-printing library](http://www.cse.chalmers.se/~rjmh/Papers/pretty.html) by John Hughes, describes the original pretty-printing library in Haskell, [PDF](http://belle.sourceforge.net/doc/hughes95design.pdf)
* [Strictly Pretty Paper](https://lindig.github.io/papers/strictly-pretty-2000.pdf) by Christian Lindig, describes a strict implementation of the pretty printing algorithm in OCaml


## Example usage

In order to use the library:

```scala mdoc
import es.weso.document._
```

Let's declare a simple `Tree` case class:

```scala mdoc
case class Tree(value: String, leaves: List[Tree])
```

We can define a `tree2Doc` function that converts trees to documents as:

```scala mdoc
def tree2Doc(tree: Tree): Document = 
 DocText(tree.value) :/: DocNest(1,list2doc(tree.leaves))

def list2doc(ls: List[Tree]): Document = 
 ls.foldLeft(Document.empty){ case (acc,t) => tree2Doc(t) :/: acc }
```

In that way, given the following tree:


```scala mdoc
val myTree = Tree("A", List(Tree("B", List()), Tree("C", List())))
```

We can convert it to a document as: 

```scala mdoc
val myDoc = tree2Doc(myTree)
```

Documents can be pretty-printed by using the `format` method that requires a `Writer` as a parameter:

```scala mdoc
import java.io._

val writer = new StringWriter()
```

The result of pretty printing to the writer can be seen by running:

```scala mdoc
myDoc.format(20, writer)

writer.toString
```
