package app.viewObject;

import lombok.Data;

@Data
public class ConsumerProductVO {

	public ConsumerProductVO() {
	}

	private Integer productId;
	private Integer orderId;
	private Integer numberOfProducts;
	private Integer totalPrice;

	private String errorMsg;
	private boolean invalidOverall;
}
