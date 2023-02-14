package ua.profitsoft.jfd.mongosample.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.profitsoft.jfd.mongosample.dto.GroupDto;
import ua.profitsoft.jfd.mongosample.dto.GroupSaveDto;
import ua.profitsoft.jfd.mongosample.dto.RestResponse;
import ua.profitsoft.jfd.mongosample.service.GroupService;

import java.util.List;


@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

  private final GroupService groupService;

  @GetMapping
  public List<GroupDto> listGroups() {
    return groupService.listGroups();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RestResponse createGroup(@Valid @RequestBody GroupSaveDto dto) {
    String id = groupService.createGroup(dto);
    return new RestResponse(String.valueOf(id));
  }
}
