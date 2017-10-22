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
@Table(name= "producer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producer {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "producer_id", unique = true, nullable = false)
	private Integer producerId;

	@Column(name = "producer_name", unique = true, nullable = false, length = 45)
	private String producerName;
	
}
