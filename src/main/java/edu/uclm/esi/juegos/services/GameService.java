package edu.uclm.esi.juegos.services;

import edu.uclm.esi.juegos.entities.Game;
import edu.uclm.esi.juegos.entities.Move;
import edu.uclm.esi.juegos.dao.GameDAO;
import edu.uclm.esi.juegos.dao.MoveDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private MoveDAO moveDAO;

    public List<Game> getAllGames() {
        return gameDAO.findAll();
    }

    public Optional<Game> getGameById(Long id) {
        return gameDAO.findById(id);
    }

    public Game saveGame(Game game) {
        return gameDAO.save(game);
    }

    public void deleteGame(Long id) {
        gameDAO.deleteById(id);
    }

    public Move saveMove(Move move) {
        return moveDAO.save(move);
    }

    public List<Move> getMovesByGameId(Long gameId) {
        return moveDAO.findByGameId(gameId);
    }
}
