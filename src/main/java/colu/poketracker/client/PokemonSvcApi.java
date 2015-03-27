package colu.poketracker.client;

import java.util.Collection;

import colu.poketracker.repository.Pokemon;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.DELETE;
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

	// Paths
	public static final String POKEMON_SVC_PATH = "/pokemon";
	public static final String POKEMON_SVC_PATH_ID = POKEMON_SVC_PATH + "/{id}";
	public static final String POKEMON_SVC_PATH_EDIT = POKEMON_SVC_PATH_ID + "/edit";

	// Paths for search
	public static final String POKEMON_NAME_SEARCH_PATH = POKEMON_SVC_PATH + "/search/findByName";
	public static final String POKEMON_SPECIES_SEARCH_PATH = POKEMON_SVC_PATH + "/search/findBySpecies";
	
	@GET(POKEMON_SVC_PATH)
	public Collection<Pokemon> getPokemonList();
	
	@GET(POKEMON_SVC_PATH_ID)
	public Pokemon getPokemonById(@Path("id") long id);
	
	@POST(POKEMON_SVC_PATH)
	public Pokemon addPokemon(@Body Pokemon p);

	@POST(POKEMON_SVC_PATH_EDIT)
	public Void editPokemon(@Path("id") long id, @Body Pokemon p);		
	
	@DELETE(POKEMON_SVC_PATH_ID)
	public Void deletePokemonById(@Path("id") long id);

	@GET(POKEMON_NAME_SEARCH_PATH)
	public Collection<Pokemon> findByName(@Query(NAME_PARAMETER) String name);
	
	@GET(POKEMON_SPECIES_SEARCH_PATH)
	public Collection<Pokemon> findBySpecies(@Query(SPECIES_PARAMETER) String species);

}
