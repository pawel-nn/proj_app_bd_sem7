package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
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
@Table(name= "log_messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogMessage {

	public LogMessage(String message, LogMessageType logMessageType) {
		this.message = message;
		this.logMessageType = logMessageType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "log_message_id", unique = true, nullable = false)
	private Integer logMessageId;

	@Column(name = "message", nullable = false)
	private String message;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "log_message_type_id")
	private LogMessageType logMessageType;

}
