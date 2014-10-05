package com.ibm.spark.kernel.protocol.v5.handler

import akka.actor.Actor
import com.ibm.spark.kernel.protocol.v5._
import com.ibm.spark.kernel.protocol.v5.content._
import com.ibm.spark.utils.LogLike
import play.api.data.validation.ValidationError
import play.api.libs.json.{Json, JsPath}
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.ask

import scala.concurrent.Future
import scala.util.{Failure, Success}

class CodeCompleteHandler(actorLoader: ActorLoader)
  extends BaseHandler(actorLoader) with LogLike
{
  override def process(kernelMessage: KernelMessage): Future[_] = {
    logger.info("Received CompleteRequest")
    val interpreterActor = actorLoader.load(SystemActorType.Interpreter)
    logger.debug(s"contentString is ${kernelMessage.contentString}")
    Json.parse(kernelMessage.contentString).validate[CompleteRequest].fold(
      (invalid: Seq[(JsPath, Seq[ValidationError])]) => {
        logger.error("Could not parse JSON for complete request!!!!!!!!!!!!!")
        throw new Throwable("Parse error in CodeCompleteHandler")
      },
      (completeRequest: CompleteRequest) => {
        logger.debug("Completion request being asked to interpreterActor")
        val codeCompleteFuture = ask(interpreterActor, completeRequest).mapTo[(Int, List[String])]
        codeCompleteFuture.onComplete {
          case Success(tuple) =>
            //  Construct a CompleteReply
            val reply = CompleteReplyOk(tuple._2, completeRequest.cursor_pos, tuple._1, Metadata())
            //  Send the CompleteReply to the Relay actor
            logger.debug("Sending complete reply to relay actor")
            actorLoader.load(SystemActorType.KernelMessageRelay) !
              kernelMessage.copy(
                //header = kernelMessage.header.copy(msg_type = MessageType.CompleteReply.toString),
                header = HeaderBuilder.create(MessageType.CompleteReply.toString),
                parentHeader = kernelMessage.header,
                contentString = Json.toJson(reply).toString
              )
          case _ =>
            new Exception("Parse error in CodeCompleteHandler")
        }

        codeCompleteFuture
      }
    )
  }
}
