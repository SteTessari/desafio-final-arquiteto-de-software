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


@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodosOrdenadosPorNome() {
        return clienteRepository.findAllOrderByNome();
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNomeParcial(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

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

    public Optional<Cliente> atualizar(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    if (!cliente.getEmail().equals(clienteAtualizado.getEmail())) {
                        Optional<Cliente> clienteComEmail = clienteRepository.findByEmail(clienteAtualizado.getEmail());
                        if (clienteComEmail.isPresent() && !clienteComEmail.get().getId().equals(id)) {
                            throw new IllegalArgumentException("Email já está em uso por outro cliente");
                        }
                    }

                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    cliente.setTelefone(clienteAtualizado.getTelefone());
                    cliente.setEndereco(clienteAtualizado.getEndereco());

                    return clienteRepository.save(cliente);
                });
    }

    public boolean deletar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public long contarClientes() {
        return clienteRepository.count();
    }

    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return clienteRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean existePorEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public Page<Cliente> buscarClientesRecentes(Pageable pageable) {
        return clienteRepository.findClientesRecentes(pageable);
    }
}

