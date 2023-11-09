package project.contcheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.contcheck.domain.Empresa;
import project.contcheck.dto.EmpresaPostResponseBody;
import project.contcheck.exceptions.EmpresaNotFoundException;
import project.contcheck.repositorys.EmpresaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public List<Empresa> listAll(){
        return empresaRepository.findAll();
    }

    public List<Empresa> findByName(String nome){
        return empresaRepository.findByNome(nome);
    }

    public List<Empresa> findByCnpj(String cnpj){
        return empresaRepository.findByCnpj(cnpj);
    }

    public Empresa save(EmpresaPostResponseBody empresaPostResponseBody){
        final var empresa = Empresa.builder()
                .cnpj(empresaPostResponseBody.cnpj())
                .nome(empresaPostResponseBody.nome())
                .build();

        return empresaRepository.save(empresa);
    }

    public void deleteByCnpj(String cnpj){
        int registrosAfetados = empresaRepository.deleteByCnpj(cnpj);
        if (registrosAfetados == 0){
            throw new EmpresaNotFoundException("Cnpj não encontrado");
        }
    }

    public void updateNomePorCnpj(String cnpj, String nome){
        int registrosAfetados = empresaRepository.updateNome(cnpj, nome);
        if (registrosAfetados == 0){
            throw new EmpresaNotFoundException("Cnpj não encontrado");
        }
    }

    public void updateCnpj(String cnpjNovo, String cnpjAntigo){
        int registrosAfetados = empresaRepository.updateCnpj(cnpjNovo, cnpjAntigo);
        if (registrosAfetados == 0){
            throw new EmpresaNotFoundException("Cnpj não encontrado");
        }
    }
}