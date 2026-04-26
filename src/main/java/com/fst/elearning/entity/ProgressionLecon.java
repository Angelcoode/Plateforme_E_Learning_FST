package com.fst.elearning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
public class ProgressionLecon {

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Utilisateur getApprenant() {
		return apprenant;
	}
	public void setApprenant(Utilisateur apprenant) {
		this.apprenant = apprenant;
	}
	public Lecon getLecon() {
		return lecon;
	}
	public void setLecon(Lecon lecon) {
		this.lecon = lecon;
	}
	public boolean isCompletee() {
		return completee;
	}
	public void setCompletee(boolean completee) {
		this.completee = completee;
	}
	public LocalDateTime getDateCompletion() {
		return dateCompletion;
	}
	public void setDateCompletion(LocalDateTime dateCompletion) {
		this.dateCompletion = dateCompletion;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apprenant_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Utilisateur apprenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecon_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Lecon lecon;

    private boolean completee;
    private LocalDateTime dateCompletion;
}
