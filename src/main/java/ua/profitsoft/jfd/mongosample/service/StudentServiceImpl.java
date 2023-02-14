package ua.profitsoft.jfd.mongosample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ua.profitsoft.jfd.mongosample.data.GroupData;
import ua.profitsoft.jfd.mongosample.data.StudentData;
import ua.profitsoft.jfd.mongosample.dto.IdentifiedName;
import ua.profitsoft.jfd.mongosample.dto.PageDto;
import ua.profitsoft.jfd.mongosample.dto.StudentDetailsDto;
import ua.profitsoft.jfd.mongosample.dto.StudentInfoDto;
import ua.profitsoft.jfd.mongosample.dto.StudentQueryDto;
import ua.profitsoft.jfd.mongosample.dto.StudentSaveDto;
import ua.profitsoft.jfd.mongosample.exceptions.NotFoundException;
import ua.profitsoft.jfd.mongosample.repository.GroupRepository;
import ua.profitsoft.jfd.mongosample.repository.StudentRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;

  private final GroupRepository groupRepository;

  @Override
  public String createStudent(StudentSaveDto dto) {
    validateStudent(dto);
    StudentData data = new StudentData();
    updateDataFromDto(data, dto);
    StudentData saved = studentRepository.save(data);
    return saved.getId();
  }

  @Override
  public StudentDetailsDto getStudent(String id) {
    StudentData data = getOrThrow(id);
    return convertToDetails(data);
  }

  @Override
  public void updateStudent(String id, StudentSaveDto dto) {
    StudentData data = getOrThrow(id);
    updateDataFromDto(data, dto);
    studentRepository.save(data);
  }

  @Override
  public List<StudentInfoDto> listStudentsByGroupId(String groupId) {
    return studentRepository.findByGroupId(groupId)
        .map(this::toInfoDto)
        .toList();
  }

  @Override
  public PageDto<StudentInfoDto> search(StudentQueryDto query) {
    Page<StudentData> page = studentRepository.search(query);
    return PageDto.fromPage(page, this::toInfoDto);
  }

  private static void validateStudent(StudentSaveDto dto) {
    if (dto.getBirthDate() != null && dto.getBirthDate().isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("birthDate should be before now");
    }
  }

  private StudentData getOrThrow(String id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Student with id '%d' not found".formatted(id)));
  }

  private StudentInfoDto toInfoDto(StudentData data) {
    return StudentInfoDto.builder()
        .id(data.getId())
        .fullName(data.getName() + " " + data.getSurname())
        .group(toGroupIdAndName(data.getGroupId()))
        .build();
  }

  private void updateDataFromDto(StudentData data, StudentSaveDto dto) {
    data.setName(dto.getName());
    data.setSurname(dto.getSurname());
    data.setBirthDate(dto.getBirthDate());
    if (!Objects.equals(data.getGroupId(), dto.getGroupId())) {
      // this method will trow and exception if a groupId isn't found
      resolveGroup(dto.getGroupId());
    }
    data.setGroupId(dto.getGroupId());
    data.setLastUpdateTime(Instant.now());
  }

  private GroupData resolveGroup(String groupId) {
    if (groupId == null) {
      return null;
    }
    return groupRepository.findById(groupId)
        .orElseThrow(() -> new NotFoundException("Group with id '%s' not found".formatted(groupId)));
  }

  private StudentDetailsDto convertToDetails(StudentData data) {
    return StudentDetailsDto.builder()
        .id(data.getId())
        .name(data.getName())
        .surname(data.getSurname())
        .birthDate(data.getBirthDate())
        .group(toGroupIdAndName(data.getGroupId()))
        .lastUpdateTime(data.getLastUpdateTime())
        .build();
  }

  private IdentifiedName toGroupIdAndName(String groupId) {
    if (groupId == null) {
      return null;
    }
    GroupData group = resolveGroup(groupId);
    return new IdentifiedName(group.getId(), group.getName());
  }

}
