package com.hayan.hello_traveler.reservation.entity;

import com.hayan.hello_traveler.accommodation.entity.Room;
import com.hayan.hello_traveler.common.entity.BaseIdEntity;
import com.hayan.hello_traveler.reservation.entity.constant.Status;
import com.hayan.hello_traveler.user.domain.Guest;
import com.hayan.hello_traveler.user.domain.Host;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(name = "reservations")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseIdEntity {

  private LocalDate checkinDate;

  private LocalDate checkoutDate;

  private Status status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "host_id", nullable = false)
  private Host host;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "guest_id", nullable = false)
  private Guest guest;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id", nullable = false)
  private Room room;

  private void changeStatus(Status newStatus) {

    if (status.canTransitionTo(newStatus)) {
      this.status = newStatus;
    } else {
      throw new IllegalStateException(
          String.format("%s에서 %s로 변경할 수 없습니다.", status, newStatus)
      );
    }
  }

  public void approve() {
    changeStatus(Status.APPROVED);
  }

  public void reject() {
    changeStatus(Status.REJECTED);
  }

  public void cancel() {
    changeStatus(Status.CANCELED);
  }

  public void complete() {
    changeStatus(Status.COMPLETED);
  }
}
