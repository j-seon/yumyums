package com.yum.yumyums.ref.serviceRef;


import com.yum.yumyums.ref.dtoRef.RefDTO;
import com.yum.yumyums.ref.entityRef.RefEntity;
import com.yum.yumyums.ref.repositoryRef.RefRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity
// Entity - DTO

@Service
@RequiredArgsConstructor
public class RefService {

    private final RefRepository refRepository;

    public void save(RefDTO refDTO) {
        RefEntity refEntity = RefEntity.toSaveEntity(refDTO);
        refRepository.save(refEntity);
    }

    public List<RefDTO> findAll() {
        List<RefEntity> refEntityEntityList = refRepository.findAll();
        List<RefDTO> refDTOList = new ArrayList<>();
        for (RefEntity refEntity: refEntityEntityList){
            refDTOList.add(RefDTO. toRefDTO(refEntity));
        }
        return refDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        refRepository.updateHits(id);
    }

    public RefDTO findById(Long id) {
        Optional<RefEntity> optionalRef = refRepository.findById(id);
        if(optionalRef.isPresent()){
            RefEntity refEntity = optionalRef.get();
            RefDTO refDTO = RefDTO.toRefDTO(refEntity);
            return refDTO;
        } else {
            return null;
        }
    }

    public RefDTO update(RefDTO refDTO) {
        RefEntity refEntity = RefEntity.toUpdateEntity(refDTO);
        refRepository.save(refEntity); // id값을 jpa가 인지하여 알아서 인서트가 아닌 업데이트를 해줌
        return findById(refDTO.getId());
    }

    public void delete(Long id) {
        refRepository.deleteById(id);
    }
}
