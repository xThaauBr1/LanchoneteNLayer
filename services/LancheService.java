package br.com.lanche.services;

import br.com.lanche.models.Lanche;

import java.io.IOException;
import java.nio.InvalidMarkException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Stream;

public class LancheService {
    private static final String PASTA_IMAGENS = "produto_imagens/";

    public String salvarImagem(Lanche lanche) throws IOException, IOException {
        String caminhoImagem = lanche.getCaminhoImagem();


        Files.createDirectories(Paths.get(PASTA_IMAGENS));


        Path destino = Paths.get(PASTA_IMAGENS + lanche.getId() + ".png");

        Files.copy(Paths.get(caminhoImagem), destino, StandardCopyOption.REPLACE_EXISTING);

        return destino.toString();
    }

    public void editarImagem(int id, String NcaminhoImagem, Lanche lanche) throws IOException {

        String OcaminhoImagem = lanche.getCaminhoImagem();
        Path caminhoImagem = Paths.get(PASTA_IMAGENS);

        String caminhoEsperado = id + ".png";

        try(Stream<Path> imagens = Files.list(caminhoImagem)){
            imagens.filter(imagem -> imagem.getFileName().toString().equals(caminhoEsperado))
                    .forEach(imagem -> {
                        try {
                            Files.deleteIfExists(imagem);
                            Path destino = Paths.get(PASTA_IMAGENS + caminhoEsperado);
                            Files.copy(Paths.get(OcaminhoImagem), destino, StandardCopyOption.REPLACE_EXISTING);
                        }catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    });
        }
    }


    public void excluirImagem(int id) throws IOException {
        Path caminhoImagem = Paths.get(PASTA_IMAGENS);
        String caminhoEsperado = id + ".png";

        try (Stream<Path> imagens = Files.list(caminhoImagem)) {
            imagens.filter(imagem -> imagem.getFileName().toString().equals(caminhoEsperado))
                    .forEach(imagem -> {
                        try {
                            Files.deleteIfExists(imagem);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

             });
        }
    }
}
