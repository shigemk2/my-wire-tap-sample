package com.example

import akka.actor._

object WireTapDriver extends CompletableApp(2) {
  val order = Order("123")
  val orderProcessor = system.actorOf(Props[OrderProcessor], "orderProcessor")
  val logger= system.actorOf(Props(classOf[MessageLogger], orderProcessor), "logger")
  val orderProcessorWireTap = logger

  orderProcessorWireTap ! ProcessOrder(order)
  awaitCompletion
  println(s"WireTap is completed.")
}

class MessageLogger(messageReceiver: ActorRef) extends Actor {
  def receive: Receive = {
    case m: Any =>
      println(s"LOG: $m")
      // forward
      messageReceiver forward m
      WireTapDriver.completedStep()
  }
}

case class Order(orderId: String)
case class ProcessOrder(order: Order)

class OrderProcessor extends Actor {
  def receive: Receive = {
    case command: ProcessOrder =>
      println(s"OrderProcessor: received: $command")
      WireTapDriver.completedStep()
  }
}