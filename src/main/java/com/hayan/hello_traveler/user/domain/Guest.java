package com.hayan.hello_traveler.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("guest")
@NoArgsConstructor
public class Guest extends User {

}
