package com.yum.yumyums.entity.chat;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.chat.ChatDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 50)
	private String name;

	public ChatDTO entityToDto() {

		ChatDTO chatDTO = new ChatDTO();
		chatDTO.setId(this.getId());
		chatDTO.setName(this.getName());

		return chatDTO;
	}
}

