## Events

All the events of the system need to contain following information as the minimum to be able to work with Event Sourcing properly.

```mermaid
  
classDiagram
   
    Event <|-- CustomerCreatedEvent
    Event <|-- CustomerUpdatedEvent
    Event <|-- CustomerDeletedEvent
   
   
    class Event {
        EventId eventId
        Instant eventDate
    }
    
    class CustomerCreatedEvent {
        User createdBy
    }
    
    class CustomerUpdatedEvent {
        int customerId
        User modifiedBy
    }
  
```