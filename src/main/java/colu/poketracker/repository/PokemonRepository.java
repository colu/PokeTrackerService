package colu.poketracker.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for a repository that can store Pokemon objects.
 *
 */
@Repository
public interface PokemonRepository extends CrudRepository<Pokemon, Long>{

	// Find all Pokemon with a matching name
	public Collection<Pokemon> findByName(String name);
	
	// Find all Pokemon with a matching species name
	public Collection<Pokemon> findBySpecies(String species);
	
	public enum PKMN {
		BULBASAUR, IVYSAUR, VENUSAUR, CHARMANDER, CHARMELEON, CHARIZARD, SQUIRTLE, WARTORTLE, BLASTOISE
	}
	
}