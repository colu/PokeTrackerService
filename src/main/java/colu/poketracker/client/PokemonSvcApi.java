package colu.poketracker.client;

import java.util.Collection;

import colu.poketracker.repository.Pokemon;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * This interface defines an API to access Pokemon data.
 * It is annotated with Retrofit.
 *
 */
public interface PokemonSvcApi {

	// Access point for the OAuth 2.0 Password Grant flow
	public static final String TOKEN_PATH = "/oauth/token";
	
	// Parameter strings
	public static final String NAME_PARAMETER = "name";
	public static final String SPECIES_PARAMETER = "species";

	// The path where we expect to find the PokemonSvc
	public static final String POKEMON_SVC_PATH = "/pokemon";

	// The path to search Pokemon by name
	public static final String POKEMON_NAME_SEARCH_PATH = POKEMON_SVC_PATH + "/search/findByName";
	
	// The path to search Pokemon by species
	public static final String POKEMON_SPECIES_SEARCH_PATH = POKEMON_SVC_PATH + "/search/findBySpecies";
	
	@GET(POKEMON_SVC_PATH)
	public Collection<Pokemon> getPokemonList();
	
	@GET(POKEMON_SVC_PATH + "/{id}")
	public Pokemon getPokemonById(@Path("id") long id);
	
	@POST(POKEMON_SVC_PATH)
	public Pokemon addPokemon(@Body Pokemon p);
	
	@GET(POKEMON_NAME_SEARCH_PATH)
	public Collection<Pokemon> findByName(@Query(NAME_PARAMETER) String name);
	
	@GET(POKEMON_SPECIES_SEARCH_PATH)
	public Collection<Pokemon> findBySpecies(@Query(SPECIES_PARAMETER) String species);

}
