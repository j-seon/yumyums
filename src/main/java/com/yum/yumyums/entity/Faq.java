package com.yum.yumyums.entity;

import com.yum.yumyums.dto.FaqDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 50)
    private String category;

    public FaqDTO entityToDto() {

        FaqDTO faqDTO = new FaqDTO();
        faqDTO.setId(this.getId());
        faqDTO.setTitle(this.getTitle());
        faqDTO.setContent(this.getContent());
        faqDTO.setCategory(this.getCategory());

        return faqDTO;
    }
}
