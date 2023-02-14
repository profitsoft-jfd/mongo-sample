package ua.profitsoft.jfd.mongosample.repository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import ua.profitsoft.jfd.mongosample.data.StudentData;
import ua.profitsoft.jfd.mongosample.dto.StudentQueryDto;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  @Override
  public Page<StudentData> search(StudentQueryDto query) {
    PageRequest pageRequest = PageRequest.of(
        query.getPage(),
        query.getSize(),
        Sort.by(Sort.Direction.ASC, StudentData.Fields.id)
    );
    Query mongoQuery = new Query().with(pageRequest);
    if (StringUtils.isNotBlank(query.getName())) {
      mongoQuery.addCriteria(where(StudentData.Fields.name).is(query.getName()));
    }
    if (StringUtils.isNotBlank(query.getSurname())) {
      mongoQuery.addCriteria(where(StudentData.Fields.surname).is(query.getSurname()));
    }
    if (StringUtils.isNotBlank(query.getGroupId())) {
      mongoQuery.addCriteria(where(StudentData.Fields.groupId).is(query.getGroupId()));
    }

    final List<StudentData> users = mongoTemplate.find(mongoQuery, StudentData.class);

    return PageableExecutionUtils.getPage(
        users,
        pageRequest,
        () -> mongoTemplate.count((Query.of(mongoQuery).limit(-1).skip(-1)), StudentData.class)
    );
  }

}
