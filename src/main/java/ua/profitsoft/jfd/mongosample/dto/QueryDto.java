package ua.profitsoft.jfd.mongosample.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for query DTOs with pagination support.
 */
@Getter
@Setter
public class QueryDto {

  @Min(0)
  private Integer page = 0;

  @Min(1)
  private Integer size = 10;

}
