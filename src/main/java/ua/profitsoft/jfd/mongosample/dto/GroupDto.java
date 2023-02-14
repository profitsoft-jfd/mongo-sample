package ua.profitsoft.jfd.mongosample.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class GroupDto {

  private String id;

  private String name;

  private Integer startYear;

}
