package com.example

import akka.actor._

object WireTapDriver extends CompletableApp(2) {
}

class MessageLogger(messageReceiver: ActorRef) extends Actor {
  def receive = {
    case m: Any =>
      println(s"LOG: $m")
      messageReceiver forward m
      WireTapDriver.completedStep()
  }
}

case class Order(orderId: String)
case class ProcessOrder(order: Order)
