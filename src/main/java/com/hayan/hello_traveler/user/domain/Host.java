package com.hayan.hello_traveler.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("host")
@NoArgsConstructor
public class Host extends User {

}
