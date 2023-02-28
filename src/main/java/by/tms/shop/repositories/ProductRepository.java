package by.tms.shop.repositories;

import by.tms.shop.entities.Category;
import by.tms.shop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);
}
