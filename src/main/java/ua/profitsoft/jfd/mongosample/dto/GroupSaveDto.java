package ua.profitsoft.jfd.mongosample.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class GroupSaveDto {

  @NotBlank
  private String name;

  private Integer startYear;

}
