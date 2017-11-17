package app.viewObject;

import lombok.Data;

@Data
public class DictionaryVO {

	public DictionaryVO() {}
	
	private Integer dictionaryId;
	private DictionaryCategoryNameVO dictionaryCategoryNameVO;
	private String dictionaryEntry;
	private Integer parentTableId;

}
