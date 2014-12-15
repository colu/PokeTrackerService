package colu.poketracker;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import colu.poketracker.client.PokemonSvcApi;
import colu.poketracker.repository.Pokemon;
import colu.poketracker.repository.PokemonRepository;

import com.google.common.collect.Lists;

@Controller
public class PokemonController {
	
	@Autowired
	private PokemonRepository pokemons;
	
	// GET /pokemon
	@RequestMapping(value=PokemonSvcApi.POKEMON_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody ArrayList<Pokemon> getPokemonList() {
		
		return Lists.newArrayList(pokemons.findAll());
	}
	
	// GET /pokemon/{id}
	@RequestMapping(value=PokemonSvcApi.POKEMON_SVC_PATH + "/{id}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Pokemon> getPokemonById(
			@PathVariable("id") long id) {
		
		Pokemon p = pokemons.findOne(id);
		
		// Return 404 if the video is not found
		if (p == null) {
			return new ResponseEntity<Pokemon>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Pokemon>(p, HttpStatus.OK);
	}
	
	// POST /pokemon
	@RequestMapping(value=PokemonSvcApi.POKEMON_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody Pokemon addPokemon(@RequestBody Pokemon p) {
		
		pokemons.save(p);
		
		return p;
	}

	// GET /pokemon/search/findByName?name={name}
	@RequestMapping(value=PokemonSvcApi.POKEMON_NAME_SEARCH_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Pokemon> findByName(
			@RequestParam(PokemonSvcApi.NAME_PARAMETER) String name) {

		return pokemons.findByName(name);
	}
	
	// GET /pokemon/search/findBySpecies?species={species}
	@RequestMapping(value=PokemonSvcApi.POKEMON_SPECIES_SEARCH_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Pokemon> findBySpecies(
			@RequestParam(PokemonSvcApi.SPECIES_PARAMETER) String species) {

		return pokemons.findBySpecies(species);
	}	

}
