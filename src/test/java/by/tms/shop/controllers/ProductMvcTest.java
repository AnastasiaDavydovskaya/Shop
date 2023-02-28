package by.tms.shop.controllers;

import by.tms.shop.dto.ProductCreatedDto;
import by.tms.shop.mapper.ProductMapper;
import by.tms.shop.repositories.BucketRepository;
import by.tms.shop.repositories.OrderRepository;
import by.tms.shop.repositories.ProductRepository;
import by.tms.shop.repositories.UserRepository;
import by.tms.shop.services.facade.ProductCategoryServicesFacade;
import by.tms.shop.services.facade.ProductFileServicesFacade;
import by.tms.shop.services.impl.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class, FlywayAutoConfiguration.class})
public class ProductMvcTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductMapper productMapper;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BucketRepository bucketRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private ProductCategoryServicesFacade productCategoryServicesFacade;
    @MockBean
    private ProductFileServicesFacade productFileServicesFacade;
    private ProductCreatedDto productCreatedDto;

    @BeforeEach
    public void init() {
        productCreatedDto = ProductCreatedDto.builder()
                .title("test")
                .nameOfPhoto("test.png")
                .price(1.0)
                .build();
    }

    @Test
    @WithMockUser
    void testGetCategoryProducts() throws Exception {
        when(productCategoryServicesFacade.findAllInCategory(1L)).thenReturn(List.of());
        when(categoryService.findAll()).thenReturn(List.of());

        mvc.perform(get("/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("customer-page"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testAddProduct() throws Exception {
        when(categoryService.findAll()).thenReturn(List.of());

        mvc.perform(get("/add/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("add-product"));
    }

}
