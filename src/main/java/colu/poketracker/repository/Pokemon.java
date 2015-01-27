package colu.poketracker.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Objects;

@Entity
public class Pokemon {
	
	// Id should uniquely identify a Pokemon
	@Id
	// Every time we create a Pokemon, we assign this value as its Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	private String species;
	
	private long trainer;
	
	private String nature;
	
	private String ability;
	
	private String item;
	
	private boolean shiny;
	
	// A Pokemon can have a maximum of 4 moves
	@ElementCollection
	private Set<String> moveset = new HashSet<String>();
	
	public enum Stat {
		HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED;
	}
	
	// Tracks which EVs the Pokemon has earned
	@ElementCollection
	private Map<Stat, Integer> evs = new HashMap<Stat, Integer>();
	
	// Tracks which IVs are perfect
	@ElementCollection
	private Set<Stat> ivs = new HashSet<Stat>();
	
	public Pokemon() {
	}

	public Pokemon(String name, String species, long trainer, String nature, String ability, String item, boolean shiny, Set<String> moveset, Map<Stat, Integer> evs, Set<Stat> ivs) {
		
		super();
		this.name = name;
		this.species = species;
		this.trainer = trainer;
		this.nature = nature;
		this.ability = ability;
		this.item = item;
		this.shiny = shiny;
		this.moveset = moveset;
		this.evs = evs;
		this.ivs = ivs;
	}
	
	public Pokemon(String name, String species, long trainer, String nature, String ability, String item, boolean shiny, Set<String> moveset) {
		
		super();
		this.name = name;
		this.species = species;
		this.trainer = trainer;
		this.nature = nature;
		this.ability = ability;
		this.item = item;
		this.shiny = shiny;
		this.moveset = moveset;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSpecies() {
		return species;
	}
	
	public void setSpecies(String species) {
		this.species = species;
	}
	
	public long getTrainer() {
		return trainer;
	}
	
	public void setTrainer(long trainer) {
		this.trainer = trainer;
	}
	
	public String getNature() {
		return nature;
	}
	
	public void setNature(String nature) {
		this.nature = nature;
	}
	
	public String getAbility() {
		return ability;
	}
	
	public void setAbility(String ability) {
		this.ability = ability;
	}
	
	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public boolean getShiny() {
		return shiny;
	}
	
	public void setShiny(boolean shiny) {
		this.shiny = shiny;
	}
	
	public Set<String> getMoveset() {
		return moveset;
	}
	
	public void setMoveset(Set<String> moveset) {
		this.moveset = moveset;
	}
	
	public Map<Stat, Integer> getEvs() {
		return evs;
	}
	
	public void setEvs(Map<Stat, Integer> evs) {
		this.evs = evs;
	}	
	
	public Set<Stat> getIvs() {
		return ivs;
	}
	
	public void setIvs(Set<Stat> ivs) {
		this.ivs = ivs;
	}
	
	@Override
	public String toString() {
		return this.name + " (" + this.species + "), " + this.nature + " nature, holding" + this.item;
	}
	
	/**
	 * Two Pokemon will generate the same hashcode if they have exactly the same
	 * values for their name, species, nature, item, shiny, moveset, evs, and ivs.
	 * 
	 */
	@Override
	public int hashCode() {
		// uses Google Guava
		return Objects.hashCode(name, species, nature, item, shiny, moveset, evs, ivs);
	}

	/**
	 * Two Pokemon are considered equal if they have exactly the same values for
	 * their name, species, nature, item, shiny, moveset, evs, and ivs.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pokemon) {
			Pokemon other = (Pokemon) obj;
			// uses Google Guava
			return Objects.equal(name, other.name)
					&& Objects.equal(species, other.species)
					&& Objects.equal(nature, other.nature)
					&& Objects.equal(item, other.item)
					&& Objects.equal(shiny, other.shiny)
					&& Objects.equal(moveset, other.moveset)
					&& Objects.equal(evs, other.evs)
					&& Objects.equal(ivs, other.ivs);
		} else {
			return false;
		}
	}

}
