package ua.profitsoft.jfd.mongosample.service;

import ua.profitsoft.jfd.mongosample.dto.PageDto;
import ua.profitsoft.jfd.mongosample.dto.StudentDetailsDto;
import ua.profitsoft.jfd.mongosample.dto.StudentInfoDto;
import ua.profitsoft.jfd.mongosample.dto.StudentQueryDto;
import ua.profitsoft.jfd.mongosample.dto.StudentSaveDto;

import java.util.List;

public interface StudentService {

  String createStudent(StudentSaveDto dto);

  StudentDetailsDto getStudent(String id);

  void updateStudent(String id, StudentSaveDto dto);

  PageDto<StudentInfoDto> search(StudentQueryDto query);

  List<StudentInfoDto> listStudentsByGroupId(String groupId);

}
