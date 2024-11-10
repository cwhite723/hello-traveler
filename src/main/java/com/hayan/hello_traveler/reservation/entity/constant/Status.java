package com.hayan.hello_traveler.reservation.entity.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.EnumSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

public enum Status {
  REQUESTED {
    @Override
    public Set<Status> allowedNextStatuses() {
      return EnumSet.of(APPROVED, REJECTED, CANCELED);
    }
  },
  APPROVED {
    @Override
    public Set<Status> allowedNextStatuses() {
      return EnumSet.of(REJECTED, CANCELED, COMPLETED);
    }
  },
  CANCELED {
    @Override
    public Set<Status> allowedNextStatuses() {
      return EnumSet.noneOf(Status.class);
    }
  },
  REJECTED {
    @Override
    public Set<Status> allowedNextStatuses() {
      return EnumSet.noneOf(Status.class);
    }
  },
  COMPLETED {
    @Override
    public Set<Status> allowedNextStatuses() {
      return EnumSet.noneOf(Status.class);
    }
  };

  public abstract Set<Status> allowedNextStatuses();

  public boolean canTransitionTo(Status newStatus) {
    return allowedNextStatuses().contains(newStatus);
  }
}