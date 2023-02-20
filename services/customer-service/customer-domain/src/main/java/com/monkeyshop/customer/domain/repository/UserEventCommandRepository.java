package com.monkeyshop.customer.domain.repository;

import com.monkeyshop.customer.domain.user.UserEvent;

public interface UserEventCommandRepository {

  UserEvent save(UserEvent userEvent);

}
