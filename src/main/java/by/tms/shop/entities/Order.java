package by.tms.shop.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

	@CreationTimestamp
	private LocalDateTime created;
	@UpdateTimestamp
	private LocalDateTime changed;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private BigDecimal sum;
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<OrderDetails> details;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Order order = (Order) o;
		return id != null && Objects.equals(id, order.id);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
