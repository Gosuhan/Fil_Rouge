package co.simplon.filrouge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import co.simplon.filrouge.controller.VehiculeController;
import co.simplon.filrouge.dao.VehiculeDAO;
import co.simplon.filrouge.model.Vehicule;
import co.simplon.filrouge.repository.VehiculeRepository;
import co.simplon.filrouge.service.VehiculeService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FilRougeApplication.class)
public class VehiculeCRUDTest {
	
	static Vehicule vehicule;
	static Vehicule newVehicul;
	static ResponseEntity<?> newVehicule;
	static ResponseEntity<?> deleteVehicule;
	static VehiculeService vehiculeService;
	
	@Autowired
	VehiculeDAO vehiculeDAO;
	
	@BeforeClass
	public static void initVehicule() throws Exception{
		vehiculeService = new VehiculeService();
		vehicule = new Vehicule();
	}
	
	@Autowired
	private VehiculeRepository vehiculeRepository;
	
	@Autowired
	private VehiculeController vehiculeController;
	
	@Rollback(true)
    @Test
	public void testUpdateVehicule() {

		newVehicule = null;
		vehicule = createMock("Peugeot", "306");
		
		try {
			newVehicul = vehiculeRepository.save(vehicule);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(newVehicul != null);
		assertEquals("Peugeot", newVehicul.getMarque());
	}
	
	@Rollback(true)
    @Test
	public void testDeleteVehicule() {

		newVehicule = null;
		vehicule = createMock("Peugeot", "306");
		
		try {
			newVehicule = vehiculeController.createVehicule(vehicule);
			deleteVehicule = vehiculeController.deleteVehicule((long) 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(deleteVehicule.getBody() == null);
	}
	
	@Rollback(true)
	@Test
	public void testInsertSuspect() {
		try {
			vehicule = createMock("Peugeot", "206");
			newVehicule = vehiculeController.createVehicule(vehicule);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(newVehicule != null);
	}
	
	private Vehicule createMock(String marque, String modele) {
		Vehicule mock = new Vehicule();
		mock.setMarque(marque);
		mock.setModele(modele);
     	mock.setId(new Long(1));

		return mock;
	}
	

}
