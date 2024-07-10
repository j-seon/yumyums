package com.yum.yumyums.ref.dtoRef;

import com.yum.yumyums.ref.entityRef.RefEntity;
import lombok.*;

import java.time.LocalDateTime;

// DTO(Data Transfer Object), VO, Bean
// 구체적으로는 조금씩 다르지만 거의다 비슷한 목적을 가지고 있다.
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class RefDTO {
    private Long id;
    private String refWriter;
    private String refPass;
    private String refTitle;
    private String refContents;
    private int refHits;
    private LocalDateTime refCreatedTime;
    private LocalDateTime refUpdatedTime;

    public static RefDTO toRefDTO(RefEntity refEntity) {
        RefDTO refDTO = new RefDTO();
        refDTO.setId(refEntity.getId());
        refDTO.setRefWriter(refEntity.getRefWriter());
        refDTO.setRefPass(refEntity.getRefPass());
        refDTO.setRefTitle(refEntity.getRefTitle());
        refDTO.setRefContents(refEntity.getRefContents());
        refDTO.setRefHits(refEntity.getRefHits());
        refDTO.setRefCreatedTime(refEntity.getCreatedTime());
        refDTO.setRefUpdatedTime(refEntity.getUpdatedTime());
        return refDTO;
    }
}
