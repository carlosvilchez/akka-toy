package com.akkatoy

import akka.actor._
import akka.util.Timeout
import scala.concurrent.duration._

class HelloActor(myName: String) extends Actor {
  def receive = {
    // (2) changed these println statements
    case "hello" => println("hello from %s".format(myName))
    case x: ActorRef => x ! "ACK"
    case _ => println("'huh?', said %s".format(myName))
  }
}

object Main extends App {
  implicit val timeout = Timeout(5 seconds) // needed for `?` below
  //  implicit def intToString(num: Numeric[String]) = num.toString

  val system = ActorSystem("HelloSystem")
  // (3) changed this line of code
  val helloActor = system.actorOf(Props(new HelloActor("Fred")), name = "helloactor")
  helloActor ! "hello"
  helloActor ! "buenos dias"
}