package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTests {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    public void PokemonService_CreatePokemon_ReturnsPokemonDto() {
        Pokemon pokemon= Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        PokemonDto pokemonDto=PokemonDto.builder().name("pikachu").type("electric").build();
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);
        PokemonDto savedPokemon=pokemonService.createPokemon(pokemonDto);
        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void pokemonService_GetALlPokemon_ReturnsResponseDto(){
        Page<Pokemon> pokemons=Mockito.mock(Page.class);
        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);
        PokemonResponse pokemonResponses=pokemonService.getAllPokemon(1,10);
        Assertions.assertThat(pokemonResponses).isNotNull();
    }

    @Test
    public void pokemonService_GetPokemonById_ReturnsPokemonDto(){
        Pokemon pokemon=Pokemon.builder()
                .name("pikachu")
                .type("electric").build();
            when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        PokemonDto ReturnedPokemonDto=pokemonService.getPokemonById(1);
        Assertions.assertThat(ReturnedPokemonDto).isNotNull();
    }

}
