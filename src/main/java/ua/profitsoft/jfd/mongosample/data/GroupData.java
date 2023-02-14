package ua.profitsoft.jfd.mongosample.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("groups")
public class GroupData {

  private String id;

  private String name;

  private Integer startYear;

}
