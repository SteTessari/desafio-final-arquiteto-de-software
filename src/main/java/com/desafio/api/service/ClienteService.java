package com.desafio.api.service;

import com.desafio.api.model.Cliente;
import com.desafio.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Classe de serviço para gerenciar operações relacionadas aos clientes.
 * Contém a lógica de negócios e atua como intermediário entre o Controller e o Repository.
 */
@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Lista todos os clientes.
     *
     * @return lista de todos os clientes
     */
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Lista todos os clientes ordenados por nome.
     *
     * @return lista de clientes ordenados por nome
     */
    @Transactional(readOnly = true)
    public List<Cliente> listarTodosOrdenadosPorNome() {
        return clienteRepository.findAllOrderByNome();
    }

    /**
     * Busca um cliente pelo ID.
     *
     * @param id o ID do cliente
     * @return Optional contendo o cliente se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Busca clientes pelo nome (busca exata).
     *
     * @param nome o nome do cliente
     * @return lista de clientes com o nome especificado
     */
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    /**
     * Busca clientes pelo nome (busca parcial, case-insensitive).
     *
     * @param nome parte do nome do cliente
     * @return lista de clientes cujo nome contém a string especificada
     */
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNomeParcial(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Busca um cliente pelo email.
     *
     * @param email o email do cliente
     * @return Optional contendo o cliente se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    /**
     * Salva um novo cliente ou atualiza um existente.
     *
     * @param cliente o cliente a ser salvo
     * @return o cliente salvo
     * @throws IllegalArgumentException se o email já estiver em uso por outro cliente
     */
    public Cliente salvar(Cliente cliente) {
        if (cliente.getId() == null) {
            if (clienteRepository.existsByEmail(cliente.getEmail())) {
                throw new IllegalArgumentException("Email já está em uso por outro cliente");
            }
        } else {
            Optional<Cliente> clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
            if (clienteExistente.isPresent() && !clienteExistente.get().getId().equals(cliente.getId())) {
                throw new IllegalArgumentException("Email já está em uso por outro cliente");
            }
        }

        cliente.setDataAtualizacao(LocalDateTime.now());
        return clienteRepository.save(cliente);
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param id o ID do cliente a ser atualizado
     * @param clienteAtualizado os dados atualizados do cliente
     * @return Optional contendo o cliente atualizado se encontrado
     * @throws IllegalArgumentException se o email já estiver em uso por outro cliente
     */
    public Optional<Cliente> atualizar(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    // Verificar se o novo email não está sendo usado por outro cliente
                    if (!cliente.getEmail().equals(clienteAtualizado.getEmail())) {
                        Optional<Cliente> clienteComEmail = clienteRepository.findByEmail(clienteAtualizado.getEmail());
                        if (clienteComEmail.isPresent() && !clienteComEmail.get().getId().equals(id)) {
                            throw new IllegalArgumentException("Email já está em uso por outro cliente");
                        }
                    }

                    // Atualizar os campos
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    cliente.setTelefone(clienteAtualizado.getTelefone());
                    cliente.setEndereco(clienteAtualizado.getEndereco());

                    return clienteRepository.save(cliente);
                });
    }

    /**
     * Deleta um cliente pelo ID.
     *
     * @param id o ID do cliente a ser deletado
     * @return true se o cliente foi deletado, false se não foi encontrado
     */
    public boolean deletar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Conta o número total de clientes.
     *
     * @return número total de clientes
     */
    @Transactional(readOnly = true)
    public long contarClientes() {
        return clienteRepository.count();
    }

    /**
     * Verifica se um cliente existe pelo ID.
     *
     * @param id o ID do cliente
     * @return true se o cliente existe, false caso contrário
     */
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return clienteRepository.existsById(id);
    }

    /**
     * Verifica se um cliente existe pelo email.
     *
     * @param email o email do cliente
     * @return true se o cliente existe, false caso contrário
     */
    @Transactional(readOnly = true)
    public boolean existePorEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    /**
     * Busca clientes recentes (últimos registros criados).
     *
     * @param pageable controla quantidade registros a retornar
     * @return lista dos clientes mais recentes ordenados por data de criação decrescente por padrão
     */
    @Transactional(readOnly = true)
    public Page<Cliente> buscarClientesRecentes(Pageable pageable) {
        return clienteRepository.findClientesRecentes(pageable);
    }
}

