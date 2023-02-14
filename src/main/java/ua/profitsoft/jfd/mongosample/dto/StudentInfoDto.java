package ua.profitsoft.jfd.mongosample.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class StudentInfoDto {

  private String id;

  private String fullName;

  private IdentifiedName group;

}
