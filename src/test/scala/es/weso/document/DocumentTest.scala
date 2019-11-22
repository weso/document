package es.weso.document
import org.scalatest._
import java.io.StringWriter

class DocumentTest extends FunSpec with Matchers {

  describe(s"Document") {
    it(s"Combine 2 null documents") {
      val w: StringWriter = new StringWriter 
      (DocNil :: DocNil).format(1,w)
      w.toString should (be(""))
    }
  }
}