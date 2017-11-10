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
@Table(name= "log_message_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogMessageType {

	public LogMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "log_message_type_id", unique = true, nullable = false)
	private Integer logMessageTypeId;

	@Column(name = "message_type", length = 45)
	private String messageType;
	
}
