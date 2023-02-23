package by.tms.shop.dto;

import by.tms.shop.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailDto {
	private String title;
	private Long productId;
	private String nameOfPhoto;
	private Double price;
	private Integer amount;
	private Double sum;

	public BucketDetailDto(Product product){
		this.title = product.getTitle();
		this.productId = product.getId();
		this.nameOfPhoto = product.getNameOfPhoto();
		this.price = product.getPrice();
		this.amount = 1;
		this.sum = product.getPrice();
	}
}
