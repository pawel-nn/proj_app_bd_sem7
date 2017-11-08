package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
	
	public User(String username, String password, String email, boolean enabled, String userRole) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.userRole = userRole;
		this.failedLogins = 0;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private Integer userId;

	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String username;
	
	@Column(name = "password", nullable = false, length = 64)
	private String password;
	
	@Column(name = "email", nullable = false, length = 45)
	private String email;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@Column(name = "user_role", nullable = false, length = 45)
	private String userRole;
	
	@Column(name = "failed_logins", nullable = false)
	private Integer failedLogins;

	public boolean hasOwnerRole() {
		if(this.userRole.equals("ROLE_OWNER")) 
			return true;
		else
			return false;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    	SimpleGrantedAuthority sga = new SimpleGrantedAuthority(userRole);
    	authorities.add(sga);
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
}
