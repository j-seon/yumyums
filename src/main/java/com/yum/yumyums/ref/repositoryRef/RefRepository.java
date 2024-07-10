package com.yum.yumyums.ref.repositoryRef;


import com.yum.yumyums.ref.entityRef.RefEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefRepository extends JpaRepository<RefEntity, Long> {
    // update ref_table set ref_hits=ref_hits+1 where id=?
    @Modifying
    @Query(value = "update RefEntity b set b.refHits=b.refHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);
}
