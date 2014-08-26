package com.akkatoy

import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec
import akka.actor.{Props, ActorSystem}
import akka.testkit.TestKit
import scala.concurrent.duration._
import akka.contrib.throttle._
import Throttler._
import scala.collection.immutable.Queue

class ThrottlerTest extends TestKit(ActorSystem("testsystem"))
with WordSpec
with MustMatchers
with StopSystemAfterAll {
  "A Throttle" must {
    "can send 3 messages per second" in {

      // A simple actor that echoes whatever it receives
      val echo = system.actorOf(Props[EchoActor])

      // The throttler for this example, setting the rate
      val throttler = system.actorOf(Props(new TimerBasedThrottler(3 msgsPer (1 second))))

      // Set the target
      throttler ! SetTarget(Some(echo))

      throttler ! Queue("1")
      throttler ! Queue("2")
      throttler ! Queue("3")
      throttler ! Queue("4")
      throttler ! Queue("5")
      throttler ! Queue("6")
      throttler ! Queue("7")

      within(2 second) {
        expectMsg("1")
        expectMsg(Queue("2"))
        expectMsg(Queue("3"))
        expectNoMsg(remaining)
      }

      within(2 second) {
        expectMsg(Queue("4"))
        expectMsg(Queue("5"))
        expectMsg(Queue("6"))
        expectNoMsg(remaining)
      }

      within(2 seconds) {
        expectMsg(Queue("7"))
      }
    }
  }
}
