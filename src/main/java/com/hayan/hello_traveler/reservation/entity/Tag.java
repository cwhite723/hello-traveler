package com.hayan.hello_traveler.reservation.entity;

import com.hayan.hello_traveler.common.entity.BaseIdEntity;
import com.hayan.hello_traveler.reservation.entity.constant.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tags")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseIdEntity {

  @Column(nullable = false)
  private String name;

  private Category category;

  @OneToMany(mappedBy = "tag", cascade = CascadeType.PERSIST)
  private final List<ReviewTag> reviewTags = new ArrayList<>();
}
