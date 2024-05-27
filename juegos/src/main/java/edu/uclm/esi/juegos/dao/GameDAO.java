package edu.uclm.esi.juegos.dao;
import edu.uclm.esi.juegos.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDAO extends JpaRepository<Game, Long> {
}
