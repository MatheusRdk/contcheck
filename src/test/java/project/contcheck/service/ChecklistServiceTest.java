package project.contcheck.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.contcheck.repositorys.ChecklistRepository;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for checklist service")
public class ChecklistServiceTest {
    @InjectMocks
    private ChecklistService checklistService;

    @Mock
    private ChecklistRepository checklistRepository;
}
