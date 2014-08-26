package com.akkatoy

import akka.actor.{ActorRef, Actor}
import com.akkatoy.EchoProtocol.EchoMsg

object EchoProtocol {
  case class EchoMsg(sender: ActorRef, msg: Any)
}

class EchoActor extends Actor {
  def receive = {
    case EchoMsg(actorRef, msg) =>
      actorRef ! msg
    case msg =>
      sender ! msg
  }
}