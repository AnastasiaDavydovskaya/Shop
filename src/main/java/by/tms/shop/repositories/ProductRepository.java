package by.tms.shop.repositories;

import by.tms.shop.entities.Bucket;
import by.tms.shop.entities.Category;
import by.tms.shop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);
}
