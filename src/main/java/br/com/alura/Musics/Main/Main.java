package br.com.alura.Musics.Main;

import br.com.alura.Musics.entity.Artista;
import br.com.alura.Musics.entity.Genero;
import br.com.alura.Musics.entity.Musica;
import br.com.alura.Musics.entity.TipoArtista;
import br.com.alura.Musics.repository.ArtistaRepository;
import br.com.alura.Musics.repository.MusicaRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);

    private final ArtistaRepository artistaRepository;
    private final MusicaRepository musicaRepository;

    public Main(ArtistaRepository artistaRepository, MusicaRepository musicaRepository) {
        this.artistaRepository = artistaRepository;
        this.musicaRepository = musicaRepository;
    }

    public void exibirMenu() {
        var opcao = -1;

        while (opcao != 0) {
            System.out.println("\n*** Screen Sound Músic ***\n");
            var menu = """
                1. Cadastrar Artista
                2. Cadastrar Música
                3. Buscar Artista
                4. Buscar Música
                5. Listar Artistas
                6. Listar Músicas
                
                0. Sair
                
                Escolha uma opção:
                """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1)
                cadastrarArtista();
            else if (opcao == 2)
                cadastrarMusica();
            else if (opcao == 3)
                buscarArtista();
            else if (opcao == 4)
                buscarMusica();
            else if (opcao == 5)
                listarArtistas();
            else if (opcao == 6)
                listarMusicas();
            else if (opcao == 0)
                System.out.println("Saindo...");
            else {
                System.out.println("Opção inválida");
            }
        }
    }

    private void cadastrarArtista() {
        var escolha = 's';

        while (escolha == 's') {
            System.out.println("Digite o nome desse artista: ");
            String nome = scanner.nextLine();
            System.out.println("Digite o tipo desse artista: (solo, dupla, banda)");
            String tipo = scanner.nextLine().toUpperCase();
            try {
                TipoArtista tipoArtista = TipoArtista.valueOf(tipo);
                Artista artista = new Artista(nome, tipoArtista);
                artistaRepository.save(artista);
                System.out.println("Artista cadastrado com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de artista inválido. Tente novamente.");
                continue;
            }

            System.out.println("Cadastrar outro artista? (S/N)");
            escolha = scanner.nextLine().toLowerCase().charAt(0);
        }
    }

    private void cadastrarMusica() {
        var escolha = 's';

        while (escolha == 's') {
            System.out.println("Digite o nome dessa música: ");
            String nome = scanner.nextLine();
            System.out.println("Digite o gênero dessa música: (funk, trap, pagode, internacional)");
            String generoMusica = scanner.nextLine().toUpperCase();
            System.out.println("Digite o álbum dessa música: ");
            String album = scanner.nextLine();
            System.out.println("Digite o nome do artista dessa música: ");
            String nomeArtista = scanner.nextLine();

            Artista artista = artistaRepository.findByNome(nomeArtista.toLowerCase());
            if (artista == null) {
                System.out.println("Artista não encontrado. Cadastre o artista primeiro.");
                break;
            }
            try {
                Genero genero = Genero.valueOf(generoMusica);
                Musica musica = new Musica(nome, genero, album, artista);
                musicaRepository.save(musica);
                System.out.println("Música cadastrada com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("Gênero da música inválido. Tente novamente.");
                continue;
            }

            System.out.println("Cadastrar outra música? (S/N)");
            escolha = scanner.nextLine().toLowerCase().charAt(0);
        }
    }

    private void buscarArtista() {
        var escolha = 's';

        while (escolha == 's') {
            System.out.println("Digite o nome do artista que você deseja buscar: ");
            String nomeArtista = scanner.nextLine();

            Artista artista = artistaRepository.findByNome(nomeArtista.toLowerCase());
            if (artista == null) {
                System.out.println("Artista não encontrado. Cadastre o artista primeiro.");
                break;
            } else {
                System.out.println("Artista encontrado!");
                System.out.println("Nome: " + artista.getNome());
                System.out.println("Tipo: " + artista.getTipoArtista());
            }

            System.out.println("Buscar outro artista? (S/N)");
            escolha = scanner.nextLine().toLowerCase().charAt(0);
        }
    }

    private void buscarMusica() {
        var escolha = 's';

        while (escolha == 's') {
            System.out.println("Digite o nome da música que você deseja buscar: ");
            String nomeMusica = scanner.nextLine();

            Musica musica = musicaRepository.findByNome(nomeMusica.toLowerCase());
            if (musica == null) {
                System.out.println("Música não encontrada. Cadastre a música primeiro.");
                break;
            } else {
                System.out.println("Música encontrada!");
                System.out.println("Nome: " + musica.getNome());
                System.out.println("Gênero: " + musica.getGenero());
                System.out.println("Álbum: " + musica.getAlbum());
                System.out.println("Artista: " + musica.getArtista().getNome());
            }

            System.out.println("Buscar outra música? (S/N)");
            escolha = scanner.nextLine().toLowerCase().charAt(0);
        }
    }

    private void listarArtistas() {
        List<Artista> artistas = artistaRepository.findAll();
        if (artistas.isEmpty()) {
            System.out.println("Nenhum artista encontrado. Cadastre um artista primeiro");
        } else {
            System.out.println("Artistas cadastrados:");
            artistas.stream()
                    .sorted(Comparator.comparing(Artista::getTipoArtista))
                    .forEach(artista -> {
                        System.out.println("--------------------");
                        System.out.println("Nome: " + artista.getNome());
                        System.out.println("Tipo: " + artista.getTipoArtista());
                        System.out.println("--------------------\n");
                    });
        }
    }

    private void listarMusicas() {
        List<Musica> musicas = musicaRepository.findAll();
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma música encontrada. Cadastre uma música primeiro");
        } else {
            System.out.println("Músicas cadastradas: ");
            musicas.stream()
                    .sorted(Comparator.comparing(musica -> musica.getArtista().getNome()))
                    .forEach(musica -> {
                        System.out.println("--------------------");
                        System.out.println("Nome: " + musica.getNome());
                        System.out.println("Gênero: " + musica.getGenero());
                        System.out.println("Álbum: " + musica.getAlbum());
                        System.out.println("Artista: " + musica.getArtista().getNome());
                        System.out.println("--------------------\n");
                    });
        }
    }
}
