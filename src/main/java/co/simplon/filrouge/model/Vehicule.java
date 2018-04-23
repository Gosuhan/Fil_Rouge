package co.simplon.filrouge.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicule",
uniqueConstraints=
@UniqueConstraint(columnNames={"marque", "modele", "immatriculation", "couleur_vehicule"}))
public class Vehicule extends Objet {

	private String immatriculation;
	private String couleur_vehicule;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "vehicule")
	@JsonIgnore
	private Set<Affaire> affaire = new HashSet<>();

	public Vehicule() {
		super();
	}

	public Vehicule(String immatriculation, String couleur_vehicule) {
		super();
		this.immatriculation = immatriculation;
		this.couleur_vehicule = couleur_vehicule;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public String getCouleur_vehicule() {
		return couleur_vehicule;
	}

	public void setCouleur_vehicule(String couleur_vehicule) {
		this.couleur_vehicule = couleur_vehicule;
	}

}
