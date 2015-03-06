package colu.poketracker.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import colu.poketracker.client.PokemonSvcApi;
import colu.poketracker.client.SecuredRestBuilder;
import colu.poketracker.client.SecuredRestException;
import colu.poketracker.repository.Pokemon;

import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.ApacheClient;

import com.google.gson.JsonObject;


/**
 * 
 * This integration test sends a POST request to the PokemonServlet to add a new
 * Pokemon and then sends a second GET request to check that the Pokemon showed up
 * in the list of Pokemons. Actual network communication using HTTP is performed
 * with this test.
 * 
 * The test requires that the PokemonSvc be running first (see the directions in
 * the README.md file for how to launch the Application).
 * 
 * To run this test, right-click on it in Eclipse and select
 * "Run As"->"JUnit Test"
 * 
 * Pay attention to how this test that actually uses HTTP and the test that just
 * directly makes method calls on a PokemonSvc object are essentially identical.
 * All that changes is the setup of the pokemonService variable. Yes, this could
 * be refactored to eliminate code duplication...but the goal was to show how
 * much Retrofit simplifies interaction with our service!
 * 
 * @author jules
 *
 */
public class PokemonSvcClientApiTest {

	private static final String USERNAME = "admin";
	private static final String PASSWORD = "pass";
	private static final String CLIENT_ID = "mobile";
	private final String READ_ONLY_CLIENT_ID = "mobileReader";

	private static final String TEST_URL = "https://localhost:8443";

	private static PokemonSvcApi pokemonService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + PokemonSvcApi.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(CLIENT_ID)
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(PokemonSvcApi.class);

	private PokemonSvcApi readOnlypokemonService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + PokemonSvcApi.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(READ_ONLY_CLIENT_ID)
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(PokemonSvcApi.class);

	private PokemonSvcApi invalidClientpokemonService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + PokemonSvcApi.TOKEN_PATH)
			.setUsername(UUID.randomUUID().toString())
			.setPassword(UUID.randomUUID().toString())
			.setClientId(UUID.randomUUID().toString())
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(PokemonSvcApi.class);

	private Pokemon mon = generateRandomPokemon();
	private static Pokemon mon1, mon2, mon3, mon4;
	
	@BeforeClass
	public static void loadData() {
		
		// Pikachu
		Set<String> moveset1 = new HashSet<String>();
		moveset1.add("Thunderbolt");
		moveset1.add("Nuzzle");
		moveset1.add("Rain Dance");
		moveset1.add("Light Screen");
		
		HashMap<Pokemon.Stat, Integer> evs1 = new HashMap<Pokemon.Stat, Integer>();
		evs1.put(Pokemon.Stat.SPEED, new Integer(252));
		
		Set<Pokemon.Stat> ivs1 = new HashSet<Pokemon.Stat>();
		ivs1.add(Pokemon.Stat.SPEED);
		
		mon1 = new Pokemon("Sparky", "Pikachu", 12345, "Timid", "Static", 
				"Light Ball", false, moveset1, evs1, ivs1);
		pokemonService.addPokemon(mon1);
		
		// Leafeon
		Set<String> moveset2 = new HashSet<String>();
		moveset2.add("Leaf Blade");
		moveset2.add("Swords Dance");
		moveset2.add("Sunny Day");
		moveset2.add("Synthesis");
		
		mon2 = new Pokemon("Blade", "Leafeon", 12345, "Adamant", "Leaf Guard", 
				"Lum Berry", false, moveset2);
		pokemonService.addPokemon(mon2);
		
		// Volcarona
		Set<String> moveset3 = new HashSet<String>();
		moveset3.add("Fiery Dance");
		moveset3.add("Quiver Dance");
		moveset3.add("Bug Buzz");
		moveset3.add("Hurricane");
		
		HashMap<Pokemon.Stat, Integer> evs3 = new HashMap<Pokemon.Stat, Integer>();
		evs3.put(Pokemon.Stat.SPECIAL_ATTACK, new Integer(252));
		evs3.put(Pokemon.Stat.SPEED, new Integer(252));
		
		Set<Pokemon.Stat> ivs3 = new HashSet<Pokemon.Stat>();
		ivs3.add(Pokemon.Stat.SPEED);
		ivs3.add(Pokemon.Stat.SPECIAL_DEFENSE);
		ivs3.add(Pokemon.Stat.SPECIAL_ATTACK);
		ivs3.add(Pokemon.Stat.HP);
		
		mon3 = new Pokemon("Scarlet", "Volcarona", 12345, "Modest", "Flame Body", 
				"Leftovers", false, moveset3, evs3, ivs3);
		pokemonService.addPokemon(mon3);
		
		// Another Pikachu
		
		mon4 = new Pokemon("Chuchu", "Pikachu", 12345, "Timid", "Static", 
				"Light Ball", false, moveset1, evs1, ivs1);
		pokemonService.addPokemon(mon4);
	}

	/**
	 * Tests verify:
	 * 	(1) Each Pokemon was added to the PokemonSvc
	 * 	(2) We can find the right Pokemon by ID
	 *  (3) We can search for Pokemon by name
	 *  (4) We can search for Pokemon by species
	 *  (5) We can delete Pokemon
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPokemonSvcClient() throws Exception {

		// (1) Each Pokemon was added to the PokemonSvc
		Collection<Pokemon> mons = pokemonService.getPokemonList();
		assertTrue(mons.contains(mon1));
		assertTrue(mons.contains(mon2));
		assertTrue(mons.contains(mon3));
		assertTrue(mons.contains(mon4));
		
		// (2) We can find the right Pokemon by ID
		assertTrue(pokemonService.getPokemonById(1).equals(mon1));
		assertTrue(pokemonService.getPokemonById(2).equals(mon2));
		assertTrue(pokemonService.getPokemonById(3).equals(mon3));
		assertTrue(pokemonService.getPokemonById(4).equals(mon4));
		
		// (3) We can search for Pokemon by name
		Collection<Pokemon> nameResults = pokemonService.findByName("Sparky");
		assertTrue(nameResults.size() == 1);
		assertTrue(nameResults.contains(mon1));
		
		Collection<Pokemon> emptyNameResults = pokemonService.findByName("Foo");
		assertTrue(emptyNameResults.size() == 0);
		
		// (4) We can search for Pokemon by species
		Collection<Pokemon> speciesResults = pokemonService.findBySpecies("Pikachu");
		assertTrue(speciesResults.size() == 2);
		assertTrue(speciesResults.contains(mon1));
		assertTrue(speciesResults.contains(mon4));
		
		// (5) We can delete Pokemon
		pokemonService.deletePokemonById(4);
		Collection<Pokemon> newMons = pokemonService.getPokemonList();
		assertFalse(newMons.contains(mon4));
	}
	
	/**
	 * This test ensures that clients with invalid credentials cannot access Pokemon.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAccessDeniedWithIncorrectCredentials() throws Exception {

		try {
			// Add the Pokemon
			invalidClientpokemonService.addPokemon(mon);

			fail("The server should have prevented the client from adding a Pokemon"
					+ " because it presented invalid client/user credentials");
		} catch (RetrofitError e) {
			assert (e.getCause() instanceof SecuredRestException);
		}
	}
	
	/**
	 * This test ensures that read-only clients can access the Pokemon list
	 * but not add new Pokemons.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReadOnlyClientAccess() throws Exception {

		Collection<Pokemon> Pokemons = readOnlypokemonService.getPokemonList();
		assertNotNull(Pokemons);
		
		try {
			// Add the Pokemon
			readOnlypokemonService.addPokemon(mon);

			fail("The server should have prevented the client from adding a Pokemon"
					+ " because it is using a read-only client ID");
		} catch (RetrofitError e) {
			JsonObject body = (JsonObject)e.getBodyAs(JsonObject.class);
			assertEquals("insufficient_scope", body.get("error").getAsString());
		}
	}
	
	private static Pokemon generateRandomPokemon() {
		
		Set<String> moveset = new HashSet<String>();
		moveset.add("Psychic");
		moveset.add("Aura Sphere");
		moveset.add("Metronome");
		moveset.add("Nasty Plot");

		return new Pokemon("Mew", "Mew", 0, "Jolly", "Synchronize", "", false, moveset);
	}

}
