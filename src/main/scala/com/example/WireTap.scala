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
  def receive: Unit = {
    case m: Any =>
      println(s"LOG: $m")
      messageReceiver forward m
      WireTapDriver.completedStep()
  }
}

case class Order(orderId: String)
case class ProcessOrder(order: Order)

class OrderProcessor extends Actor {
  def receive: Unit = {
    case command: ProcessOrder =>
      println(s"OrderProcessor: received: $command")
      WireTapDriver.completedStep()
  }
}