package edu.uclm.esi.juegos.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uclm.esi.juegos.services.Connect4Service;

@RestController  // ESTO INDICA QUE ESTA CLASE RECIBE MENSAJES HTTP
@RequestMapping("wins")  //NOMBRE CON EL QUE SE DA A CONOCER EL CONTROLADOR EN ESTA RED 

public class Connect4Controller {
	
	//NECESITAMOS UNA INSTANCIA DEL SERVICIO PARA ASI PODER REALIZAR EL PASO DE MENSAJES, PARA QUE EL SERVICIO TRABAJE DE ACORDE A LO RECIBIDO POR EL CONTROLADOR
	@Autowired
	private Connect4Service connect4Service;
	
	
	@GetMapping("/makeMove")
	public void makeMovement(@RequestParam String board[][]) {
		
	}
	
	@GetMapping("/checkWinner") // ESTE METODO SE EXPONE COMO UN SERVICIO WEB DE TIPO GET CUYO NOMBRE PUBLICO ES EL DE LA ETIQUETA
	public boolean checkWinner(@RequestParam String board[][], int lastP[]) {
		return this.connect4Service.checkWinner(board, lastP);
	}
	
	
	
}
