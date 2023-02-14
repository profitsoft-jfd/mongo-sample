package ua.profitsoft.jfd.mongosample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.profitsoft.jfd.mongosample.data.GroupData;
import ua.profitsoft.jfd.mongosample.dto.GroupDto;
import ua.profitsoft.jfd.mongosample.dto.GroupSaveDto;
import ua.profitsoft.jfd.mongosample.repository.GroupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;

  @Override
  public List<GroupDto> listGroups() {
    return groupRepository.findAll().stream()
        .map(this::dataToDto)
        .toList();
  }

  @Override
  public String createGroup(GroupSaveDto dto) {
    GroupData data = new GroupData();
    copyToData(dto, data);
    GroupData saved = groupRepository.save(data);
    return saved.getId();
  }

  private static void copyToData(GroupSaveDto dto, GroupData data) {
    data.setName(dto.getName());
    data.setStartYear(dto.getStartYear());
  }

  private GroupDto dataToDto(GroupData data) {
    return GroupDto.builder()
        .id(data.getId())
        .name(data.getName())
        .startYear(data.getStartYear())
        .build();
  }

}
