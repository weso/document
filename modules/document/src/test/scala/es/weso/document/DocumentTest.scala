package es.weso.document
import java.io.StringWriter
import munit.FunSuite

class DocumentTest extends FunSuite {

 test(s"Combine 2 null documents same line") {
      val w: StringWriter = new StringWriter 
      (DocNil :: DocNil).format(1,w)
      assertEquals(w.toString, "")
   }

 test(s"Combine 2 null documents next line") {
      val w: StringWriter = new StringWriter
      (DocNil :: DocNil).format(1,w)
      assertEquals(w.toString, "")
    }

  
 test("Combine 2 text documents same line") {
      val w: StringWriter = new StringWriter
      (DocText("a") :: DocText("b")).format(1,w)
      assertEquals(w.toString, "ab")
    }

 test("Combine 2 text documents next line") {
      val w: StringWriter = new StringWriter
      (DocText("a") :/: DocText("b")).format(1,w)
      assertEquals(w.toString, "a\nb")
    }

 test("Combine 2 group documents same line") {
      val w: StringWriter = new StringWriter
      (DocGroup(DocText("a")) :: DocGroup(DocText("b"))).format(1,w)
      assertEquals(w.toString, "ab")
    }

 test("Combine 2 group documents next line") {
      val w: StringWriter = new StringWriter
      (DocGroup(DocText("a")) :/: DocGroup(DocText("b"))).format(1,w)
      assertEquals(w.toString, "a\nb")
    }

 test("Combine 2 nested documents same line") {
      val w: StringWriter = new StringWriter
      (DocNest(1, DocText("a")) :: DocNest(1, DocText("b"))).format(1,w)
      assertEquals(w.toString, "ab")
    }

 test("Combine 2 nested documents next line") {
      val w: StringWriter = new StringWriter
      (DocNest(1, DocText("a")) :/: DocNest(1, DocText("b"))).format(1,w)
      assertEquals(w.toString, "a\nb")
 }

 test("Combine a text document and a string in same line") {
      val w: StringWriter = new StringWriter
      ("a" :: DocText("b")).format(1,w)
      assertEquals(w.toString, "ab")
 }

 test("Combine a text document and a string in next line") {
      val w: StringWriter = new StringWriter
      ("a" :/: DocText("b")).format(1,w)
      assertEquals(w.toString, "a\nb")
    }

 test("Create an empty text document") {
      val w: StringWriter = new StringWriter
      Document.empty.format(1,w)
      assertEquals(w.toString, "")
    }

 test("Create a break document") {
      val w: StringWriter = new StringWriter
      Document.break.format(1,w)
      assertEquals(w.toString, " ")
    }

 test("Create a text document from a string") {
      val w: StringWriter = new StringWriter
      Document.text("a").format(1,w)
      assertEquals(w.toString, "a")
    }

 test("Create a document group from a single doc") {
      val w: StringWriter = new StringWriter
      Document.group(DocText("a")).format(1,w)
      assertEquals(w.toString, "a")
  }

 test("Create a document nest from a single doc") {
      val w: StringWriter = new StringWriter
      Document.nest(1, DocText("a")).format(1,w)
      assertEquals(w.toString, "a")
  } 
}