package project.contcheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.contcheck.domain.Empresa;
import project.contcheck.repositorys.EmpresaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public List<Empresa> listAll(){
        return empresaRepository.findAll();
    }
}
