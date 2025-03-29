package br.com.alura.Musics;

import br.com.alura.Musics.Main.Main;
import br.com.alura.Musics.repository.ArtistaRepository;
import br.com.alura.Musics.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicsApplication implements CommandLineRunner {

	@Autowired
	ArtistaRepository artistaRepository;

	@Autowired
	MusicaRepository musicaRepository;

	public static void main(String[] args) {
		SpringApplication.run(MusicsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(artistaRepository, musicaRepository);
		main.exibirMenu();
	}
}
