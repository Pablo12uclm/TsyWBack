/* package edu.uclm.esi.juegos.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uclm.esi.auxi.Manager;
import edu.uclm.esi.juegos.dto.ApiResponse;


@RestController
@RequestMapping("games")
@CrossOrigin(origins = "*")
public class GameController extends CookiesController {


 @PostMapping("/cuatroEnRaya/checkStatus")
 public ResponseEntity<ApiResponse> checkCuatroEnRayaStatus(@RequestBody CheckStatusDto checkStatusDto) {
     CuatroEnRayaService cuatroEnRayaService = Manager.get().getCuatroEnRayaService();
     ResultDto result = cuatroEnRayaService.evaluateGameStatus(checkStatusDto);
     return ApiResponse.ok(result);
 }
 
 
 @PostMapping("/playMatch")
 public ResponseEntity<Match> playMatch(@RequestBody MatchRequestDto matchRequest) {
     WaitingRoomService waitingRoomService = Manager.get().getWaitingRoomService();
     Match match = waitingRoomService.attemptToStartMatch(matchRequest.getUserId(), matchRequest.getGameName());
     return match != null ? ResponseEntity.ok(match) : ResponseEntity.noContent().build();
 }

 @PostMapping("/removeMatch")
 public ResponseEntity<?> removeMatch(@RequestBody MatchRemovalDto matchRemoval) {
     WaitingRoomService waitingRoomService = Manager.get().getWaitingRoomService();
     waitingRoomService.terminateMatch(matchRemoval.getMatchId(), matchRemoval.getGameName());
     return ResponseEntity.ok().build();
 }
 
}
     
*/