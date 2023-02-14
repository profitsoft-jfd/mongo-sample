package ua.profitsoft.jfd.mongosample.service;

import ua.profitsoft.jfd.mongosample.dto.GroupDto;
import ua.profitsoft.jfd.mongosample.dto.GroupSaveDto;

import java.util.List;

public interface GroupService {
  List<GroupDto> listGroups();

  String createGroup(GroupSaveDto dto);
}
