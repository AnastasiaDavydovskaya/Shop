package by.tms.shop.services.impl;

import by.tms.shop.dto.*;
import by.tms.shop.entities.*;
import by.tms.shop.exceptions.NotFoundUserException;
import by.tms.shop.repositories.BucketRepository;
import by.tms.shop.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final OrderService orderService;

    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        bucketRepository.save(bucket);
        return bucket;
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    public Bucket addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductsList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductsList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductsList);
        bucketRepository.save(bucket);

        return bucket;
    }

    public void deleteProduct(Long productId, String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            throw new NotFoundUserException("Пользователь не найден: " + login);
        }

        Bucket bucket = user.getBucket();
        List<Long> productsId = bucket.getProducts().stream()
                .map(Product::getId)
                .filter(id -> !id.equals(productId))
                .toList();

        bucket.getProducts().clear();
        bucketRepository.save(bucket);

        addProducts(bucket, productsId);
    }

    public Bucket minusProduct(Long productId, String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            throw new NotFoundUserException("Пользователь не найден: " + login);
        }

        Bucket bucket = user.getBucket();

        List<Product> products = bucket.getProducts();
        for(Product product: products) {
            if(product.getId().equals(productId)) {
                products.remove(product);
                break;
            }
        }
        bucketRepository.save(bucket);

        return bucket;
    }

    public BucketDto getBucketByUser(String name) {
        User user = userService.findByLogin(name);
        if (user == null || user.getBucket() == null) {
            return new BucketDto();
        }

        BucketDto bucketDto = new BucketDto();
        Map<Long, BucketDetailDto> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailDto detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new BucketDetailDto(product));
            } else {
                detail.setAmount(detail.getAmount() + 1);
                detail.setSum(detail.getSum() + product.getPrice());
            }
        }

        bucketDto.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDto.aggregate();

        return bucketDto;
    }

    public Order commitBucketToOrder(String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            throw new NotFoundUserException("Пользователь не найден");
        }

        Bucket bucket = user.getBucket();
        if (bucket == null || bucket.getProducts().isEmpty()) {
            throw new NotFoundUserException("Корзина пользователя не найдена");
        }

        Order order = new Order();
        order.setUser(user);

        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(BigDecimal.valueOf(detail.getAmount())))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setDetails(orderDetails);
        order.setSum(total);

        orderService.saveOrder(order);
        bucket.getProducts().clear();
        bucketRepository.save(bucket);

        return order;
    }

}
