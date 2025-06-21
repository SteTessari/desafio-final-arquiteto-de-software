package com.desafio.api.repository;

import com.desafio.api.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    List<Cliente> findByNome(String nome);

    @Query("select c from Cliente c where upper(c.nome) like upper(concat(?1, '%'))")
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    Optional<Cliente> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Cliente> findByTelefone(String telefone);

    List<Cliente> findByEnderecoContainingIgnoreCase(String endereco);

    @Query("SELECT COUNT(c) FROM Cliente c")
    long contarClientes();

    @Query("SELECT c FROM Cliente c ORDER BY c.nome ASC")
    List<Cliente> findAllOrderByNome();

    @Query("SELECT c FROM Cliente c ORDER BY c.dataCriacao DESC")
    Page<Cliente> findClientesRecentes(Pageable pageable);
}

