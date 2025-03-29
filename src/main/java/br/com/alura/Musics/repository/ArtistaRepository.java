package br.com.alura.Musics.repository;

import br.com.alura.Musics.entity.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    @Query("SELECT a FROM Artista a WHERE LOWER(a.nome) = :nomeArtista")
    Artista findByNome(String nomeArtista);
}
