package app.dataTransportObject;

import app.viewObject.ConsumerProductVO;


public class ConsumerProductDTO extends ValidationDTO<ConsumerProductVO> {

	public ConsumerProductDTO(ConsumerProductVO consumerProductVO) {
		super(consumerProductVO, false);
	}

}
