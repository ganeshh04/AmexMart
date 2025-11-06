package com.amexmart.repository;

import com.amexmart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByActiveTrue(Pageable pageable);

    Page<Product> findByCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.active = true AND " +
            "(:minPrice IS NULL OR p.discountedPrice >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.discountedPrice <= :maxPrice)")
    Page<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice,
                                   @Param("maxPrice") BigDecimal maxPrice,
                                   Pageable pageable);

    List<Product> findTop10ByOrderByCreatedAtDesc();

    List<Product> findTop10ByOrderByAverageRatingDesc();
}
