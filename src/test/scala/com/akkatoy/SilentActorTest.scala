package com.akkatoy

import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec
import akka.actor.{Actor, Props, ActorSystem}
import akka.testkit.{TestActorRef, TestKit}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import akka.contrib.throttle._
import Throttler._
import scala.collection.immutable.Queue


class SilentActorTest extends TestKit(ActorSystem("testsystem"))
with WordSpec
with MustMatchers
with StopSystemAfterAll {
  "A Silent Actor" must {
    "change state when it receives a message, single threaded" in {
      import SilentActorProtocol._

      val silentActor = TestActorRef[SilentActor]
      silentActor ! SilentMessage("whisper")
      silentActor.underlyingActor.state must (contain("whisper"))
    }

    "change state when it receives a message, multi-threaded" in {
      import SilentActorProtocol._

      val silentActor = system.actorOf(Props[SilentActor], "s3")
      silentActor ! SilentMessage("whisper1")
      silentActor ! SilentMessage("whisper2")
      silentActor ! GetState(testActor)
      expectMsg(Vector("whisper1", "whisper2"))

      // expectNoMsg() has to wait for a timeout

      //Write the test, first fail
      //        fail("not implemented yet")
    }

    "playing with Game" in {
      import SilentActorProtocol._

      val silentActor = system.actorOf(Props[SilentActor], "sgame")
      silentActor ! Game(testActor, "name", 5)
      expectMsgPF() {
        case Game(_, _, tickets) =>
          tickets must be(4)
      }
    }
  }
}
