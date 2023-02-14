package ua.profitsoft.jfd.mongosample.repository;

import org.springframework.data.domain.Page;
import ua.profitsoft.jfd.mongosample.data.StudentData;
import ua.profitsoft.jfd.mongosample.dto.StudentQueryDto;

public interface StudentRepositoryCustom {

  Page<StudentData> search(StudentQueryDto query);

}
