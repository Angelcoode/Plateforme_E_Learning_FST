package com.fst.learning.entity;

import com.fst.learning.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Utilisateur {
	@Id
	@GeneratedValue
	private Long id;

	private String nom;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;
}