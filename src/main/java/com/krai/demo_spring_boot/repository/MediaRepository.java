package com.krai.demo_spring_boot.repository;

import com.krai.demo_spring_boot.models.MediaModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<MediaModel, Long> {
  List<MediaModel> findByProductModelIdOrderBySortOrderAscIdAsc(Long productId);
}
