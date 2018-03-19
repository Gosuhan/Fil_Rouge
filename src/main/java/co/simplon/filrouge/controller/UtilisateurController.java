package co.simplon.filrouge.controller;

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

import co.simplon.filrouge.model.Utilisateur;
import co.simplon.filrouge.service.UtilisateurService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UtilisateurController {

	@Autowired
	private UtilisateurService utilisateurService;

	@GetMapping(path = "/utilisateurs")
	public @ResponseBody Iterable<Utilisateur> getAllUtilisateurs() throws Exception {
		return utilisateurService.getAllUtilisateurs();
	}

	@GetMapping(path = "/utilisateur/{id}")
	public @ResponseBody Utilisateur getUtilisateur(@PathVariable Long id) throws Exception {
		return utilisateurService.getUtilisateur(id);
	}

	@DeleteMapping(path = "/utilisateur/{id}")
	public @ResponseBody void deleteUtilisateur(@PathVariable Long id) {
		utilisateurService.delete(id);
	}

	@PostMapping(path = "/utilisateurs")
	public ResponseEntity<?> createUtilisateur(@RequestBody Utilisateur user) throws Exception {
		Utilisateur newUser = utilisateurService.addUtilisateur(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}

	@PutMapping(path = "/utilisateurs")
	public ResponseEntity<?> updateUtilisateur(@RequestBody Utilisateur user) throws Exception {
		Utilisateur updatedUser = utilisateurService.editUtilisateur(user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedUser);
	}
}