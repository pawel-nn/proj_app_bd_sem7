package app.dataTransportObject;

import app.viewObject.ProducerVO;

public class ProducerDTO extends ValidationDTO<ProducerVO> {

	public ProducerDTO(ProducerVO ProducerVO) {
		super(ProducerVO, false);
	}
	
}
