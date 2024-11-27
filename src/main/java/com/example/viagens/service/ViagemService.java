package com.example.viagens.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.viagens.entity.Viagem;
import com.example.viagens.repository.ViagemRepository;

@Service
public class ViagemService {
    private final ViagemRepository viagemRepository;

     public ViagemService (ViagemRepository viagemRepository) {
        this.viagemRepository = viagemRepository;
     }

     public List<Viagem> getViagens() {
        return viagemRepository.findAll();
    }

    public Viagem criarViagem(Viagem viagem) {
        return viagemRepository.save(viagem);
    }

    public Optional<List<Viagem>> pesquisarViagem(String valor) {
        List<Viagem> viagens = getViagens();
        List<Viagem> viagensFiltradas = viagens.stream()
            .filter(viagem -> viagem.getNome().toLowerCase().contains(valor.toLowerCase()) && 
                              viagem.getLocalizacao().toLowerCase().contains(valor.toLowerCase()))
            .collect(Collectors.toList());
        return Optional.of(viagensFiltradas); 
    }

    public Optional<Viagem> getDetalhes(Long id) {
        return viagemRepository.findById(id);
    }

    public Viagem adicionarAvaliacao(Long id, Integer novaAvaliacao) {
        if (novaAvaliacao < 0 || novaAvaliacao > 10) {
            throw new IllegalArgumentException("A avaliação deve estar entre 0 e 10.");
        }

        Optional<Viagem> viagemOpt = viagemRepository.findById(id);

        if (viagemOpt.isPresent()) {
            Viagem viagem = viagemOpt.get();

            viagem.getAvaliacoes().add(novaAvaliacao);
            Double novaMedia = viagem.calcularMediaAvaliacoes();
            viagem.setNotaMedia(novaMedia);

            return viagemRepository.save(viagem);
        }

        throw new IllegalArgumentException("Destino não encontrado.");
    }

    public void excluirViagem(Long id) {
        viagemRepository.deleteById(id);
    }
}
