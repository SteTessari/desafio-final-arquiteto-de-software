package com.desafio.api.repository;

import com.desafio.api.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface de repositório para a entidade Cliente.
 * Estende JpaRepository para fornecer operações CRUD básicas e métodos personalizados.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca clientes pelo nome (busca exata).
     *
     * @param nome o nome do cliente
     * @return lista de clientes com o nome especificado
     */
    List<Cliente> findByNome(String nome);

    /**
     * Busca clientes pelo nome (busca parcial, case-insensitive).
     *
     * @param nome parte do nome do cliente
     * @return lista de clientes cujo nome contém a string especificada
     */
    @Query("select c from Cliente c where upper(c.nome) like upper(concat(?1, '%'))")
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca um cliente pelo email.
     *
     * @param email o email do cliente
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> findByEmail(String email);

    /**
     * Verifica se existe um cliente com o email especificado.
     *
     * @param email o email a ser verificado
     * @return true se existe um cliente com o email, false caso contrário
     */
    boolean existsByEmail(String email);

    /**
     * Busca clientes pelo telefone.
     *
     * @param telefone o telefone do cliente
     * @return lista de clientes com o telefone especificado
     */
    List<Cliente> findByTelefone(String telefone);

    /**
     * Busca clientes cujo endereço contém a string especificada (case-insensitive).
     *
     * @param endereco parte do endereço
     * @return lista de clientes cujo endereço contém a string especificada
     */
    List<Cliente> findByEnderecoContainingIgnoreCase(String endereco);

    /**
     * Conta o número total de clientes no sistema.
     *
     * @return número total de clientes
     */
    @Query("SELECT COUNT(c) FROM Cliente c")
    long contarClientes();

    /**
     * Busca clientes ordenados por nome.
     *
     * @return lista de clientes ordenados por nome
     */
    @Query("SELECT c FROM Cliente c ORDER BY c.nome ASC")
    List<Cliente> findAllOrderByNome();

    /**
     * Busca clientes criados recentemente (últimos N registros).
     *
     * @param pageable controla quantidade registros a retornar
     * @return lista dos clientes mais recentes ordenados por data de criação decrescente por padrão
     */
    @Query("SELECT c FROM Cliente c ORDER BY c.dataCriacao DESC")
    Page<Cliente> findClientesRecentes(Pageable pageable);
}

