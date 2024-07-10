package com.yum.yumyums.ref.entityRef;

import com.yum.yumyums.ref.dtoRef.RefDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ref_table")
public class RefEntity extends BaseRefEntity {
    @Id //  pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false)
    private String refWriter;

    @Column
    private String refPass;

    @Column
    private String refTitle;

    @Column(length = 500)
    private String refContents;

    @Column
    private int refHits;

    public static RefEntity toSaveEntity(RefDTO refDTO){
        RefEntity refEntity = new RefEntity();
        refEntity.setRefWriter(refDTO.getRefWriter());
        refEntity.setRefPass(refDTO.getRefPass());
        refEntity.setRefTitle(refDTO.getRefTitle());
        refEntity.setRefContents(refDTO.getRefContents());
        refEntity.setRefHits(0);
        return refEntity;
    }

    public static RefEntity toUpdateEntity(RefDTO refDTO) {
        RefEntity refEntity = new RefEntity();
        refEntity.setId(refDTO.getId()); // id 값이 있어야 update가 된다
        refEntity.setRefWriter(refDTO.getRefWriter());
        refEntity.setRefPass(refDTO.getRefPass());
        refEntity.setRefTitle(refDTO.getRefTitle());
        refEntity.setRefContents(refDTO.getRefContents());
        refEntity.setRefHits(refDTO.getRefHits());
        return refEntity;
    }
}
