package com.example.viagens.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.viagens.entity.Viagem;
import com.example.viagens.service.ViagemService;

@RestController
@RequestMapping("/viagens")
public class ViagemController {
    @Autowired    
    private ViagemService viagemService;

    @PostMapping
    public ResponseEntity<?> criarViagem(@RequestBody Viagem viagem) {
        return new ResponseEntity<>(viagemService.criarViagem(viagem), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getViagem() {
        return new ResponseEntity<>(viagemService.getViagens(), HttpStatus.OK);
    }

    @GetMapping("pesquisa/{procurar}")
    public ResponseEntity<?> getViagemPesquisada(@PathVariable String procurar) {
        Optional<List<Viagem>> viagemPesquisa = viagemService.pesquisarViagem(procurar);
        if(viagemPesquisa.isPresent())
            return new ResponseEntity<>(viagemPesquisa.get(), HttpStatus.OK);
        return new ResponseEntity<>("Viagem com esse destino não encontrada!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> getViagemDetalhes(@PathVariable Long codigo) {
        Optional<Viagem> detalhesViagem = viagemService.getDetalhes(codigo);
        if(detalhesViagem.isPresent())
            return new ResponseEntity<>(detalhesViagem.get(), HttpStatus.OK);
        return new ResponseEntity<>("Viagem não encontrada!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/avaliar")
    public ResponseEntity<Viagem> avaliarDestino(@PathVariable Long id, @RequestParam Integer novaAvaliacao) {
        try {
            Viagem destino = viagemService.adicionarAvaliacao(id, novaAvaliacao);
            return ResponseEntity.ok(destino);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> deletePedido(@PathVariable Long codigo) {
        viagemService.excluirViagem(codigo);
        return new ResponseEntity<>("Pedido Excluído do Sucesso!", HttpStatus.OK);
    }
}
