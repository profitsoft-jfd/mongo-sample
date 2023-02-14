package ua.profitsoft.jfd.mongosample.data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ua.profitsoft.jfd.mongosample.dto.Address;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@FieldNameConstants
@Document("students")
public class StudentData {

  @Id
  private String id;

  private String name;

  private String surname;

  private String groupId;

  private LocalDate birthDate;

  private List<String> phoneNumbers;

  private Address address;

  private Instant lastUpdateTime;

}
