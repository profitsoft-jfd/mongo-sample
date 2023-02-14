package ua.profitsoft.jfd.mongosample.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@EqualsAndHashCode
@RequiredArgsConstructor
public class IdentifiedName {

  private final String id;

  private final String name;

}
