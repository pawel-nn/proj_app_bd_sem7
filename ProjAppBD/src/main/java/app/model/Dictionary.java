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
	@Column(name = "dictionary_entry_id", unique = true, nullable = false)
	private Integer dictionaryEntryId;

	@ManyToOne
	@JoinColumn(name = "category_name")
	private DictionaryCategoryName dictionaryCategoryName;

	@Column(name = "parent_table_id", nullable = false)
	private Integer parentTableId;
	
}
