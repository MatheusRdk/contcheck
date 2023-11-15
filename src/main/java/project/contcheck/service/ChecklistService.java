package project.contcheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.contcheck.domain.Checklist;
import project.contcheck.dto.ChecklistPostRequestBody;
import project.contcheck.exceptions.ChecklistNotFoundException;
import project.contcheck.exceptions.EmpresaNotFoundException;
import project.contcheck.repositorys.ChecklistRepository;
import project.contcheck.repositorys.EmpresaRepository;
import project.contcheck.util.Status;
import project.contcheck.util.Tipo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;

    private final EmpresaRepository empresaRepository;

    public List<Checklist> listAll(){
        return checklistRepository.findAll();
    }

    public Checklist findByIdOrThrowNotFoundException(long id) {
        return checklistRepository.findById(id)
                .orElseThrow(() -> new ChecklistNotFoundException("Checklist not found."));
    }

    public List<Checklist> findByTipo(String tipo){
        return checklistRepository.findByTipo(Tipo.fromString(tipo));
    }

    public List<Checklist> findByMesAno(String mesAno){
        return checklistRepository.findByMesAno(mesAno);
    }

    public List<Checklist> findByStatus(String status){
        return checklistRepository.findByStatus(Status.fromString(status));
    }

    public List<Checklist> findChecklistByCnpj(String cnpj){
        return checklistRepository.findByCnpj(cnpj);
    }

    @Transactional
    public Checklist save(ChecklistPostRequestBody checklistPostRequestBody){
        final var checklist = Checklist.builder()
                .tipo(Tipo.fromString(checklistPostRequestBody.tipo()))
                .mesAno(checklistPostRequestBody.mesAno())
                .status(Status.fromString(checklistPostRequestBody.status()))
                .empresa(empresaRepository.findById(checklistPostRequestBody.empresaId()).orElseThrow(() -> new EmpresaNotFoundException("Empresa Id not found")))
                .build();

        return checklistRepository.save(checklist);
    }

    @Transactional
    public void deleteAllChecklistByCnpj(String cnpj){
        int registrosAfetados = checklistRepository.deleteAllByCnpj(cnpj);
        if (registrosAfetados == 0){
            throw new EmpresaNotFoundException("Cnpj não encontrado");
        }
    }

    public void deleteChecklistById(Long id){
        checklistRepository.delete(findByIdOrThrowNotFoundException(id));
    }
}