package app.dataTransportObject;

import lombok.Data;

@Data
abstract class ValidationDTO<T> {
	
	private T viewObject;
	private boolean validationResult;
	
	public ValidationDTO(T viewObject, boolean validationResult) {
		this.viewObject = viewObject;
		this.validationResult = validationResult;
	}
	
}
