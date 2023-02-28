package by.tms.shop.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "orders_details")
public class OrderDetails extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private Long amount;
	private BigDecimal price;

	public OrderDetails(Order order, Product product, Long amount) {
		this.order = order;
		this.product = product;
		this.amount = amount;
		this.price = BigDecimal.valueOf(product.getPrice());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		OrderDetails that = (OrderDetails) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
