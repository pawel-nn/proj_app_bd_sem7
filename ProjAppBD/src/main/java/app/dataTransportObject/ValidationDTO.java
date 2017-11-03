package app.dataTransportObject;

import lombok.Data;

@Data
abstract class ValidationDTO<T> {
	
	private T viewObject;
	private boolean valid;
	private boolean persistStatus;
	
	public ValidationDTO(T viewObject, boolean validationResult) {
		this.viewObject = viewObject;
		this.valid = validationResult;
		this.persistStatus = false;
	}
	
}