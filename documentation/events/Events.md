## Events

All the events of the system need to contain following information as the minimum to be able to work with Event Sourcing properly.

```mermaid
  
classDiagram
   
    Event <|-- UserCreatedEvent
    Event <|-- UserUpdatedEvent
    Event <|-- UserDeletedEvent
   
   
    class Event {
        EventId eventId
        String type
        Instant timestamp
        String author
    }
    
    class UserCreatedEvent {
        String username
        String email
        String passwordHash
        String role
    }
    
    class UserUpdatedEvent {
        String userId
        String username
        String email
        String passwordHash
        String role
    }
    
    class UserDeletedEvent {
        String userId 
    }
  
```