package ua.profitsoft.jfd.mongosample.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Builder
public class StudentDetailsDto {

  private String id;

  private String name;

  private String surname;

  private LocalDate birthDate;

  private IdentifiedName group;

  private Instant lastUpdateTime;

}
