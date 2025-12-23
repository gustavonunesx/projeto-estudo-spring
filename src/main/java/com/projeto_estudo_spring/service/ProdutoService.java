package com.projeto_estudo_spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_estudo_spring.exception.RecursoNotFoundException;
import com.projeto_estudo_spring.model.Produto;
import com.projeto_estudo_spring.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repository;

    public Produto salvarProduto(Produto produto){
        return repository.save(produto);
    }

    public List<Produto> listarProdutos(){
        return repository.findAll();
    }

    public Produto buscarPorId(Long id){
        return repository.findById(id)
        .orElseThrow(() -> new RecursoNotFoundException("Produto com Id " +id+" não encontrado"));
    }

    public void deletarProduto(Long id){

        if (!repository.existsById(id)) {
            throw new RecursoNotFoundException("Produto com Id " +id+ " não encontrado");
        }else{
            repository.deleteById(id);
        }
        
    }



}
