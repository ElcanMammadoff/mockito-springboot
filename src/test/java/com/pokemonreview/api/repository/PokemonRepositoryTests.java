package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTests {

    @Autowired
    private  PokemonRepository pokemonRepository;

    @Test
    public void pokemonRepository_SaveAll_ReturnSavedPokemon(){

        //Arrange
       Pokemon pokemon=Pokemon.builder()
               .name("pikachu")
               .type("electric")
               .build();


        //Act
            Pokemon savedPokemon=pokemonRepository.save(pokemon);

        //Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    public void pokemonRepository_getAll_returnMoreThenOnePokemon(){
        Pokemon pokemon=Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        Pokemon pokemon2=Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList=pokemonRepository.findAll();
        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    public void pokemonRepository_findById_returnPokemon(){
        Pokemon pokemon=Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);
        Pokemon returnedPokemon=pokemonRepository.findById(pokemon.getId()).get();
        Assertions.assertThat(returnedPokemon).isNotNull();
    }

    @Test
    public void pokemonRepository_findByType_returnPokemonNotNull(){
        Pokemon pokemon=Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);
        Pokemon pokemonList=pokemonRepository.findByType(pokemon.getType()).get();
        Assertions.assertThat(pokemonList).isNotNull();
    }

    @Test
    public void pokemonRepository_updatePokemon_returnPokemonNotNull(){
        Pokemon pokemon=Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);
        Pokemon pokemonSave=pokemonRepository.findById(pokemon.getId()).get();
        pokemonSave.setType("Electric");
        pokemonSave.setName("Ritachu");
        Pokemon pokemonUpdate=pokemonRepository.save(pokemonSave);
        Assertions.assertThat(pokemonUpdate.getName()).isNotNull();
        Assertions.assertThat(pokemonUpdate.getType()).isNotNull();
    }

    @Test
    public void pokemonRepository_DeletePokemon_returnPokemonIsEmpty(){
        Pokemon pokemon=Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);
        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonReturn=pokemonRepository.findById(pokemon.getId());
        Assertions.assertThat(pokemonReturn).isNotNull();
    }
}
