# Event-Delivery Pattern: Producer-Consumer


In this repository I have implemented a simple example of a producer-consumer pattern using Kafka.
The producer application creates "Purchase" events that represent some item being purchased. We can change the
number of partitions by overriding the environment variable KAFKA_PARTITIONS defined in the producer.

We can change how many consumers each deployment of the consumer application should use by overriding
the KAFKA_CONSUMER_COUNT environment variable. We can set groupId per deployment by overriding the KAFKA_GROUP_ID
environment variable if we want each deployment to share event offset within the same group.


## What is the idea

Decouple applications by passing events asynchronously through a message broker.
Ensure availability as long the broker is available.
Message queues can handle bursts of traffic that would overload the consuming services.
Backpressure can be achieved by having the events buffer in the broker. Consuming services can pull events
as fast as possible without getting overwhelmed. 

## When to use

* If availability is more important than consistency, and you can get away with eventual consistency.

* If you need to handle large bursts of traffic, and you can't slow down the producer.



## Things to consider

### How to monitor and handle queue size. 

Queue sizes can be either bound to a fixed size or unbound. 
Unbound queues are problematic because of several reasons. When disk/memory runs out, there is no good options left other
than dropping every incoming request or scrambling to add more resources.

If there are consistently more incoming events than are consumed, the queue is bound to run out of resources.
It is an indication that the consumers can't keep up and some action must be taken. Furthermore, this can lead to very high latency if the queue is large.
Queue buildup can occur due to several reasons.

The queue can be configured to be ordered within a single partition. To maintain order within a single partition,
there can only be one concurrent consumer of events. Meaning there is no way to scale how many consumers
can consume events in parallel. There is no good solution to this other than to add faster hardware unless 
you can find a way to loosen the requirements on event order.

The event processing can be expensive causing the consumers to fall behind. In this case we can either
add more consumers or more queue partitions. Alternatively, if possible, we can slow down the producers when the queue gets 
to large.

### Delivery Guarantees

I'm not going to cover delivery guarantees in detail since I have written about them here https://github.com/endj/Kafka-Delivery-Guarantees/blob/master/document/doc.pdf 
The two most common guarantees brokers can guarantee is at-least once or at-most once delivery. Exactly-one deliver often requires
additional code to guarantee. If the application can get away with losing occasional events, we can use at-most once delivery, aka Fire-and-Forget.
An example might be if we are taking real-time weather measurements. If we were to drop an event, there is no point in trying to recover it since 
it might be outdated by the time we process it ( Assuming we get events often and what is acceptable lag ).

If the results of processing an event is idempotent, we can use at-least once delivery for exactly once processing.

### Queue partitions

There are many types of message brokers but they generally all support partitioning a queue in multiple partitions.
If we need to ensure that we process events in the order they were written, we most likely need to limit the queue topic to a single partition.
If we have several partitions, the events within the partition might be ordered, but they won't be ordered across the topic.

### Log-based brokers

Log based brokers such as Kafka don't remove events after they are consumed. This is useful if several applications
want to consume the same events or to be able to replay old events.

