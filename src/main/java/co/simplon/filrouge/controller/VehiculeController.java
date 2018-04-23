package co.simplon.filrouge.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.filrouge.dao.VehiculeDAO;
import co.simplon.filrouge.model.Affaire;
import co.simplon.filrouge.model.AffaireLien;
import co.simplon.filrouge.model.Arme;
import co.simplon.filrouge.model.Vehicule;
import co.simplon.filrouge.service.VehiculeService;

/**
 * 
 * @author Davy
 *
 */

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class VehiculeController {

	@Autowired
	private VehiculeService vehiculeService;
	@Autowired
	private VehiculeDAO vehiculeDAO;

	/* Exemple requête SQL :
	 * SELECT * FROM filrouge.vehicule; 
	 */
	@GetMapping(path = "/vehicules")
	public @ResponseBody Iterable<Vehicule> getAllVehicules() throws Exception {
		return vehiculeService.getAllVehicules();
	}
	
	/**
	 * Permettre une recherche de véhicule(s) suivant des mots-clés
	 * 
	 * @param recherche
	 * @return
	 * @throws Exception
	 */
	@GetMapping(path = "/vehicules/{recherche}")
	public ResponseEntity<List<Vehicule>> recupererVehiculesTriees(@PathVariable(value = "recherche") String recherche)
			throws Exception {
		// @RequestParam(required = false, value="marque") String marque

		List<Vehicule> listeVehicule = vehiculeDAO.recupererVehiculesTriees(recherche);
		if (listeVehicule == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(listeVehicule);
	}
	
	/**
	 * Ajouter un véhicule à une affaire
	 * 
	 * @param affaireLien
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/affaire/lierVehicule")
	void lierVehiculeAffaire(@Valid @RequestBody AffaireLien affaireLien) throws Exception {
		long id_affaire = affaireLien.getIdAffaire();
		long id_vehicule = affaireLien.getIdObjet();
		vehiculeDAO.lierVehiculeAffaire(id_affaire, id_vehicule);
		return;

	}
	
	/**
	 * Supprimer un véhicule d'une affaire
	 * 
	 * @param affaireLien
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(path = "/affaire/{id_affaire}/vehicule/{id}")
	public ResponseEntity<?> deleteVehiculeAffaire(@PathVariable(value = "id_affaire") long id_affaire,
			@PathVariable(value = "id") long id) throws Exception {
		try {
		vehiculeDAO.deleteFromAffaire(id_affaire, id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	/* Exemple requête SQL :
	 * SELECT * FROM filrouge.vehicule WHERE id = 1;
	 */
	@GetMapping(path = "/vehicule/{id}")
	public @ResponseBody Vehicule getVehicule(@PathVariable long id) throws Exception {
		return vehiculeService.getVehicule(id);
	}

	/* Exemple requête SQL :
	 * DELETE FROM vehicule WHERE id = 1;
	 */
//	@DeleteMapping(path = "/vehicule/{id}")
//	public @ResponseBody void deleteVehicule(@PathVariable long id) {
//		vehiculeService.delete(id);
//	}
	
	@DeleteMapping(path = "/vehicule/{id}")
	public ResponseEntity<Vehicule> deleteVehicule(@PathVariable Long id) throws Exception {
		Vehicule vehicule = vehiculeService.getVehicule(id);
		if (vehicule == null) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		else {
			vehiculeService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).build();
			}
	}

	/* exemple requête SQL :
	 * INSERT INTO vehicule (`infos_complementaire`,`marque`,`modele`,`type`,
	 * `couleur_vehicule`,`immatriculation`) VALUES ("Voiture bélier","Peugeot","306","cc","Rouge Lucifer",
	 * "115AKA75");
	 */
	@PostMapping(path = "/vehicules")
	public ResponseEntity<?> createVehicule(@RequestBody Vehicule vehicule) throws Exception {
		Vehicule newVehicule = vehiculeService.addVehicule(vehicule);
		return ResponseEntity.status(HttpStatus.CREATED).body(newVehicule);
	}

	/* exemple requête SQL :
	 * UPDATE vehicule SET `marque` = "Peugeot",`modele` = "306" WHERE `id` = 19;
	 */
	@PutMapping(path = "/vehicule/{id}")
	ResponseEntity<Vehicule> updateVehicule(@PathVariable(value = "id") long id, @Valid @RequestBody Vehicule vehicule) throws Exception {
		Vehicule vehiculeAModifier = vehiculeService.getVehicule(id);
		if (vehiculeAModifier == null)
			return ResponseEntity.notFound().build();

		// Mise à jour des attributs obligatoires
		vehiculeAModifier.setId(vehicule.getId());
		
		// Mise à jour des attributs non null
		if (vehicule.getType() != null)
			vehiculeAModifier.setType(vehicule.getType());

		if (vehicule.getMarque() != null)
			vehiculeAModifier.setMarque(vehicule.getMarque());
		
		if (vehicule.getModele() != null)
			vehiculeAModifier.setModele(vehicule.getModele());

		if (vehicule.getInfos_complementaire() != null)
			vehiculeAModifier.setInfos_complementaire(vehicule.getInfos_complementaire());

		if (vehicule.getImmatriculation() != null)
			vehiculeAModifier.setImmatriculation(vehicule.getImmatriculation());

		if (vehicule.getCouleur_vehicule() != null)
			vehiculeAModifier.setCouleur_vehicule(vehicule.getCouleur_vehicule());

		Vehicule vehiculeModifiee = vehiculeService.editVehicule(id, vehiculeAModifier);
		return ResponseEntity.ok(vehiculeModifiee);
	}
	
	@GetMapping(path = "/vehicule/{id}/affaires")
	public ResponseEntity<?> recupererAffairesDeVehicule(@PathVariable(value = "id") long id) throws Exception {
		List<Affaire> affaires = 	null;
		Vehicule vehicule = vehiculeService.getVehicule(id);
		try {
		affaires = vehiculeDAO.recupererAffairesDeVehicule(id);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			
		}
		if (vehicule == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(affaires);

		
	}

}
