package ua.profitsoft.jfd.mongosample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.profitsoft.jfd.mongosample.data.GroupData;

@Repository
public interface GroupRepository extends MongoRepository<GroupData, String> {

}
