package by.tms.shop.controllers;

import by.tms.shop.exceptions.ResourceNotFoundException;
import by.tms.shop.repositories.*;
import by.tms.shop.services.impl.OrderService;
import by.tms.shop.services.impl.UserService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class, FlywayAutoConfiguration.class})
public class OrderMvcTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private BucketRepository bucketRepository;
    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    @WithMockUser
    void testDeleteOrder() throws Exception {
        doThrow(new ResourceNotFoundException("Пользователь не найден.")).when(orderService).deleteById(1L);

        mvc.perform(get("/order/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
