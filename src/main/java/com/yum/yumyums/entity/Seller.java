package com.yum.yumyums.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;


@Entity
@Data
@Table(name = "seller")
@Getter
public class Seller {

    @Id
    @Column(length = 50)
    private String id;

    //TODO 나머지 컬럼들 만들어야 합니다.

}