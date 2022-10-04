package com.project.QR.memu.repository;

import com.project.QR.memu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
  @Query(value = "SELECT * FROM MENU WHERE MENU_ID = :menuId AND BUSINESS_ID = :businessId", nativeQuery = true)
  Optional<Menu> findByIdAndBusinessId(@Param("menuId") long menuId,
                                       @Param("businessId") long businessId);

  @Query(value = "SELECT * FROM MENU WHERE BUSINESS_ID = :businessId", nativeQuery = true)
  Page<Menu> findAllByBusinessId(@Param("businessId") long businessId,
                                 PageRequest pageRequest);
}
