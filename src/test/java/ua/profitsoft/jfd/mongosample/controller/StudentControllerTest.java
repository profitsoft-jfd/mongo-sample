package ua.profitsoft.jfd.mongosample.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import ua.profitsoft.jfd.mongosample.MongoSampleApplication;
import ua.profitsoft.jfd.mongosample.TestUtil;
import ua.profitsoft.jfd.mongosample.data.GroupData;
import ua.profitsoft.jfd.mongosample.data.StudentData;
import ua.profitsoft.jfd.mongosample.dto.IdentifiedName;
import ua.profitsoft.jfd.mongosample.dto.PageDto;
import ua.profitsoft.jfd.mongosample.dto.RestResponse;
import ua.profitsoft.jfd.mongosample.dto.StudentInfoDto;
import ua.profitsoft.jfd.mongosample.dto.StudentQueryDto;
import ua.profitsoft.jfd.mongosample.repository.GroupRepository;
import ua.profitsoft.jfd.mongosample.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests REST endpoints /students/* provided by {@link StudentController}.
 */
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = MongoSampleApplication.class)
@AutoConfigureMockMvc
class StudentControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  GroupRepository groupRepository;

  @Autowired
  StudentRepository studentRepository;

  @AfterEach
  void afterEach() {
    studentRepository.deleteAll();
    groupRepository.deleteAll();
  }

  @Test
  void testCreateStudent_success() throws Exception {
    String groupId = groupRepository.save(buildGroup("Group1")).getId();
    String name = "Taras";
    String surname = "Shevchenko";
    String body = """
          {
              "name": "%s",
              "surname": "%s",
              "groupId": "%s"
          }          
        """.formatted(name, surname, groupId);
    MockHttpServletResponse response = mvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse();
    RestResponse result = TestUtil.parseJson(response.getContentAsString(), RestResponse.class);
    String studentId = result.getResult();

    assertThat(studentId).isNotEmpty();

    Optional<StudentData> student = studentRepository.findById(studentId);
    assertThat(student).isPresent();
    assertThat(student.get().getName()).isEqualTo(name);
    assertThat(student.get().getSurname()).isEqualTo(surname);
    assertThat(student.get().getGroupId()).isEqualTo(groupId);
  }

  /**
   * Tests {@link StudentController#search(StudentQueryDto) method}.
   */
  @Test
  void testSearchStudents_byName() throws Exception {
    String groupId1 = groupRepository.save(buildGroup("Group1")).getId();
    String groupId2 = groupRepository.save(buildGroup("Group2")).getId();

    studentRepository.save(buildStudent("Taras", "Shevchenko", groupId1));
    studentRepository.save(buildStudent("Andrii", "Shevchenko", groupId2));
    studentRepository.save(buildStudent("Ivan", "Franko", groupId1));
    studentRepository.save(buildStudent("Taras", "Zubenko", groupId2));

    String body = """
          {
              "name": "%s"
          }          
        """.formatted("Taras");
    MockHttpServletResponse response = mvc.perform(post("/api/students/_search")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();
    PageDto<StudentInfoDto> result = TestUtil.parseJson(response.getContentAsString(), new TypeReference<>() {});
    assertThat(result.getTotalSize()).isEqualTo(2);
    assertThat(result.getTotalPages()).isEqualTo(1);
    assertThat(result.getList()).hasSize(2);
    assertThat(result.getList().get(0).getFullName()).isEqualTo("Taras Shevchenko");
    assertThat(result.getList().get(0).getGroup()).isEqualTo(new IdentifiedName(groupId1, "Group1"));
    assertThat(result.getList().get(1).getFullName()).isEqualTo("Taras Zubenko");
    assertThat(result.getList().get(1).getGroup()).isEqualTo(new IdentifiedName(groupId2, "Group2"));
  }

  /**
   * Tests {@link StudentController#listStudentsByGroupId(String)} method}.
   */
  @Test
  void testListStudentsByGroupId() throws Exception {
    String groupId1 = groupRepository.save(buildGroup("Group1")).getId();
    String groupId2 = groupRepository.save(buildGroup("Group2")).getId();

    studentRepository.save(buildStudent("Taras", "Shevchenko", groupId1));
    studentRepository.save(buildStudent("Andrii", "Shevchenko", groupId2));
    studentRepository.save(buildStudent("Ivan", "Franko", groupId1));
    studentRepository.save(buildStudent("Taras", "Zubenko", groupId2));

    MockHttpServletResponse response = mvc.perform(get("/api/students/byGroupId/" + groupId1)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();
    List<StudentInfoDto> result = TestUtil.parseJson(response.getContentAsString(), new TypeReference<>() {});
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getFullName()).isEqualTo("Taras Shevchenko");
    assertThat(result.get(0).getGroup()).isEqualTo(new IdentifiedName(groupId1, "Group1"));
    assertThat(result.get(1).getFullName()).isEqualTo("Ivan Franko");
  }

  private static StudentData buildStudent(String name, String surname, String groupId1) {
    StudentData result = new StudentData();
    result.setName(name);
    result.setSurname(surname);
    result.setGroupId(groupId1);
    return result;
  }

  private static GroupData buildGroup(String name) {
    GroupData result = new GroupData();
    result.setName(name);
    return result;
  }

}
