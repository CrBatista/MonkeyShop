package com.monkeyshop.customer.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document
public class UserCreatedEvent extends UserEvent {

  @Field("data.username")
  private String username;

  @Field("data.email")
  private String email;

  @Field("data.passwordHash")
  private String passwordHash;

  @Field("data.role")
  private String role;

  public UserCreatedEvent() {
    super(null, null, null, null);
  }

  @JsonCreator
  public UserCreatedEvent(@JsonProperty("author") String author,
      @JsonProperty("username") String username,
      @JsonProperty("email") String email,
      @JsonProperty("passwordHash") String passwordHash) {
    super("created", author);
    this.username = username;
    this.email = email;
    this.passwordHash = passwordHash;
  }

  public UserCreatedEvent(String author, String username, String email, String passwordHash, String role) {
    super("created", author);
    this.username = username;
    this.email = email;
    this.passwordHash = passwordHash;
    this.role = role;
  }
}
