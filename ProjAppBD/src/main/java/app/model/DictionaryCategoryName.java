package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "dictionary_category_names")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryCategoryName {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "dictionary_category_name_id", unique = true, nullable = false)
	private Integer dictionaryCategoryNameId;

	@Column(name = "category_name", length = 45)
	private String categoryName;
	
}
