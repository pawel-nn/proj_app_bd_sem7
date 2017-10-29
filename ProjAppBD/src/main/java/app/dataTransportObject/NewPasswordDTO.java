package app.dataTransportObject;

import app.viewObject.NewPasswordVO;

public class NewPasswordDTO extends ValidationDTO<NewPasswordVO>{

	public NewPasswordDTO(NewPasswordVO viewObject) {
		super(viewObject, false);
	}
	
}
