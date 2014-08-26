package com.akkatoy

import akka.actor.{ActorRef, Actor}
import com.akkatoy.SilentActorProtocol.{Game, GetState, SilentMessage}

object SilentActorProtocol {
  case class SilentMessage(data: String)
  case class GetState(receiver: ActorRef)
  case class Game(receiver: ActorRef, name: String, tickets: Integer)
}

class SilentActor extends Actor {
  var internalState = Vector[String]()

  def receive = {
    case SilentMessage(data) => internalState = internalState :+ data
    case GetState(receiver) => receiver ! internalState
    case game @ Game(receiver, _, tickets) =>
      receiver ! game.copy(tickets = tickets - 1)
  }

  def state = internalState
}

