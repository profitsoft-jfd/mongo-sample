package ua.profitsoft.jfd.mongosample.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

  private String country;

  private String town;

  private String addressString;

}
