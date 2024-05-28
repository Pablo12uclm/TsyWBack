package edu.uclm.esi.juegos.dao;

import edu.uclm.esi.juegos.entities.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MoveDAO extends JpaRepository<Move, Long> {
    List<Move> findByGameId(Long gameId);
}
