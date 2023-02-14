package ua.profitsoft.jfd.mongosample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.profitsoft.jfd.mongosample.data.StudentData;

import java.util.stream.Stream;

public interface StudentRepository extends MongoRepository<StudentData, String>, StudentRepositoryCustom {

  // запит буде формуватися автоматично за назвою метода
  Stream<StudentData> findByGroupId(String groupId);

}
