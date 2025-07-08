
package com.jsp.SpringBoot_React.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;
	private String phone;
	private String email;
	 @Column(length = 1000) 
	private String address;
	@Column(name = "is_admin")
	@JsonProperty("isAdmin")
	private int isAdmin;
	


	// Unidirectional one-to-many relationships (no @JoinColumn)
	@OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> cartItems;

	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<WishlistItem> wishlistItems;

	 @JsonManagedReference
	 @OneToMany(mappedBy = "user", fetch = FetchType.LAZY ,cascade = CascadeType.REMOVE, orphanRemoval = true)
	    private List<MyOrder> myOrders;

}
