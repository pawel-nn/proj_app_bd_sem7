package app.viewObject;

import lombok.Data;

@Data
public class ProducerVO {
	
	public ProducerVO() {}
	
	private Integer producerId;
	private String producerName;
	private Integer uId;
	
	
	public void setUp(String producerName, Integer producerId) {
		this.producerId = producerId;
		this.uId = producerId;
		this.producerName = producerName;
	}

}
