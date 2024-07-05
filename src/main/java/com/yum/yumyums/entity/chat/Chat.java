package com.yum.yumyums.entity.chat;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 50)
	private String name;
}
