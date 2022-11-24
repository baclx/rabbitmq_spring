## Rabbitmq

1: About rabbitmq need to understand some concepts as follows:
**producer:** Message sending application.

**consumer:** The application receives the message.

**queue:** Store messages.

**message:** Information passes from Producer to Consumer via RabbitMQ.

**connection:** A TCP connection between the application and the RabbitMQ broker.

**channel:** A virtual connection in a Connection. Publishing or consuming from a queue is done on the channel.

**exchange:** Is the place to receive messages published from the Producer and push them to the queue based on the rules of each Exchange type. To receive messages, the queue must be in at least one Exchange.

**binding:** Take on the task of linking Exchange and Queue.

**routingKey:** A key that Exchange relies on to decide how to route messages to the queue. In a nutshell, the Routing key is the address for the message.

**AMQP:** Advanced Message Queuing Protocol, which is the message transfer protocol in RabbitMQ.

**user:** To be able to access RabbitMQ, we must have a username and password. In RabbitMQ, Each user is assigned with a certain authority. Users can be assigned special permissions to a certain Vhost.

**virtualHost/vHost:** Provides separate ways for applications to share a RabbitMQ instance. Different users can have different permissions for different vhosts. Queue and Exchange can be created, so they only exist in one vhost.


![enter image description here](https://images.viblo.asia/a1571d98-cb4e-4f3a-9757-117a492be32c.png)

2: Types of Exchange

- 2.1 - **Direct Exchange:** Direct Exchange transports msg to the queue based on the routing key. Usually used for work **unicast** message routing (although there is a possibility used for **multicast** routing).
Msg routing steps:
>**Step**:
> + a queue is bound to a direct exchange by a routing key
> + when there is a new msg with routing key R to direct exchange. Msg will be moved to that queue if R=K

pom.xml
```java
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

**Impl Code:**
- Direct Config:
```java
import org.springframework.amqp.core.Binding;  
import org.springframework.amqp.core.BindingBuilder;  
import org.springframework.amqp.core.DirectExchange;  
import org.springframework.amqp.core.Queue;  
  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
  
@Configuration  
public class RabbitMQDirectConfig {  
  @Bean  
  Queue marketingQueue() {  
  return new Queue("marketingQueue", false);  
    }  
  
  @Bean  
  Queue financeQueue() {  
  return new Queue("financeQueue", false);  
    }  
  
  @Bean  
  Queue adminQueue() {  
  return new Queue("adminQueue", false);  
    }  
  
  @Bean  
  DirectExchange exchange() {  
  return new DirectExchange("direct-exchange");  
    }  
  
  @Bean  
  Binding marketingBinding(Queue marketingQueue, DirectExchange exchange) {  
  return BindingBuilder.bind(marketingQueue).to(exchange).with("marketing");  
    }  
  
  @Bean  
  Binding financeBinding(Queue financeQueue, DirectExchange exchange) {  
  return BindingBuilder.bind(financeQueue).to(exchange).with("finance");  
    }  
  
  @Bean  
  Binding adminBinding(Queue adminQueue, DirectExchange exchange) {  
  return BindingBuilder.bind(adminQueue).to(exchange).with("admin");  
    }  
}
```
In the above example, we will configure 3 queues and define them as a bean and similar to exchange then we will define beans to bind from exchange to queue with routingKey.

- Direct Controller
```java
import org.springframework.amqp.core.AmqpTemplate;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestParam;  
import org.springframework.web.bind.annotation.RestController;  
  
@RestController  
@RequestMapping("/rabbitmq/direct")  
public class RabbitMQDirectWebController {  
  @Autowired  
  private AmqpTemplate amqpTemplate;  
  
  @GetMapping("/producer")  
  public String producer(
	  @RequestParam("exchangeName") String exchange,
	  @RequestParam("routingKey") String routingKey,
      	  @RequestParam("messageData") String messageData  
  ) {
	  amqpTemplate.convertAndSend(exchange, routingKey, messageData);
	  
          return "Message sent to the RabbitMQ Direct Exchange Successfully";  
    }  
}
```
Start the app and go to the link: `localhost:8080/rabbitmq/direct/producer?exchangeName=direct-exchange&routingKey=admin&messageData=Belloo`

![enter image description here](https://www.javainuse.com/3-rabbit-direct-exchange-queues-min.JPG)

An exchange named direct-exchange is created with following bindings.

![enter image description here](https://www.javainuse.com/3-rabbit-direct-exchange-bindings-min.JPG)


