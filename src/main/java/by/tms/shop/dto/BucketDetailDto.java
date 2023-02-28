package by.tms.shop.dto;

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
}
