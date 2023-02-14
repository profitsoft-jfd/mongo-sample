package ua.profitsoft.jfd.mongosample.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentQueryDto extends QueryDto {

  private String name;

  private String surname;

  private String groupId;

}
