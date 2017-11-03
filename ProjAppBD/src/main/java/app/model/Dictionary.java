package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "dictionary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dictionary {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "dictionary_id", unique = true, nullable = false)
	private Integer dictionaryId;

	@ManyToOne
	@JoinColumn(name = "dictionary_category_name_id")
	private DictionaryCategoryName dictionaryCategoryName;

	@Column(name = "dictionary_entry", nullable = false)
	private String dictionaryEntry;
	
	@Column(name = "parent_table_id", nullable = false)
	private Integer parentTableId;
	
}
