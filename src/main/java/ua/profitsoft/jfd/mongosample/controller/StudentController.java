package ua.profitsoft.jfd.mongosample.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.profitsoft.jfd.mongosample.dto.PageDto;
import ua.profitsoft.jfd.mongosample.dto.RestResponse;
import ua.profitsoft.jfd.mongosample.dto.StudentDetailsDto;
import ua.profitsoft.jfd.mongosample.dto.StudentInfoDto;
import ua.profitsoft.jfd.mongosample.dto.StudentQueryDto;
import ua.profitsoft.jfd.mongosample.dto.StudentSaveDto;
import ua.profitsoft.jfd.mongosample.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RestResponse createStudent(@Valid @RequestBody StudentSaveDto dto) {
    String id = studentService.createStudent(dto);
    return new RestResponse(String.valueOf(id));
  }

  @GetMapping("/{id}")
  public StudentDetailsDto getStudent(@PathVariable String id) {
    return studentService.getStudent(id);
  }

  @PutMapping("/{id}")
  public RestResponse updateStudent(@Valid @PathVariable String id, @RequestBody StudentSaveDto dto) {
    studentService.updateStudent(id, dto);
    return new RestResponse("OK");
  }

  @GetMapping("/byGroupId/{groupId}")
  public List<StudentInfoDto> listStudentsByGroupId(@PathVariable String groupId) {
    return studentService.listStudentsByGroupId(groupId);
  }

  @PostMapping("_search")
  public PageDto<StudentInfoDto> search(@RequestBody StudentQueryDto query) {
    return studentService.search(query);
  }

}
