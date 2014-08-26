package com.akkatoy


import akka.util.Timeout
import akka.actor._
import scala.concurrent.duration._
import akka.contrib.throttle._
import Throttler._
import akka.event.LoggingReceive
import scala.collection.immutable.Queue
import akka.contrib.throttle.Throttler.SetTarget
import scala.Some

object MainThrottle extends App {
  implicit val timeout = Timeout(5 seconds)

  val system = ActorSystem("TSystem")

  // A simple actor that prints whatever it receives
  val printer = system.actorOf(Props(new Actor {
    def receive = LoggingReceive {
      case x => println(x)
    }
  }))


  val throttler = system.actorOf(Props(classOf[TimerBasedThrottler], 3 msgsPer 1.second))
  //    with ActorLogging {
  //  override def aroundReceive(recv: Receive, msg: Any) {
  //     log.info(msg.toString)
  //     super.aroundReceive(recv, msg)
  //  }
  //}
  //))

  // Set the target
  throttler ! SetTarget(Some(printer))

  // These three messages will be sent to the printer immediately
  throttler ! Queue("1")
  throttler ! Queue("2")
  throttler ! Queue("3")

  // These two will wait at least until 1 second has passed
  throttler ! Queue("4")
  throttler ! Queue("5")
}