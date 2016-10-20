package hmda.validation.rules.lar

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import hmda.model.fi.lar.LoanApplicationRegister
import hmda.validation.dsl.{ Failure, Success }
import hmda.validation.rules.AggregateEditCheck
import hmda.validation.rules.lar.`macro`.MacroEditTypes._
import org.scalatest._
import org.scalatest.prop.PropertyChecks

import scala.concurrent.{ ExecutionContext, Future }

abstract class SummaryEditCheckSpec extends PropSpec with PropertyChecks with MustMatchers {

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  def check: AggregateEditCheck[LoanApplicationRegisterSource, LoanApplicationRegister]

  implicit class SummaryChecker(input: LoanApplicationRegisterSource)(implicit ec: ExecutionContext) {
    def mustFail: Future[Assertion] = check(input).map(x => x mustBe a[Failure])
    def mustPass: Future[Assertion] = check(input).map(x => x mustBe a[Success])
  }

}
