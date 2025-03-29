package br.com.alura.Musics.repository;

import br.com.alura.Musics.entity.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    @Query("SELECT m FROM Musica m WHERE LOWER(m.nome) = :nomeMusica")
    Musica findByNome(String nomeMusica);
}
