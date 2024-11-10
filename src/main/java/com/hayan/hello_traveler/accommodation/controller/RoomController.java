package com.hayan.hello_traveler.accommodation.controller;

import static com.hayan.hello_traveler.common.response.SuccessCode.SUCCESS;

import com.hayan.hello_traveler.accommodation.entity.dto.RoomRequest;
import com.hayan.hello_traveler.accommodation.service.RoomService;
import com.hayan.hello_traveler.common.annotation.LoginCheck;
import com.hayan.hello_traveler.common.annotation.LoginHostId;
import com.hayan.hello_traveler.common.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accommodation/room")
public class RoomController {
  private final RoomService roomService;

  @LoginCheck
  @PostMapping("/{accommodation-id}")
  public ApplicationResponse<Long> create(@LoginHostId Long hostId,
      @PathVariable("accommodation-id") Long accommodationId,
      @RequestBody RoomRequest request) {
    Long roomId = roomService.save(hostId, accommodationId, request);

    return ApplicationResponse.ok(roomId, SUCCESS);
  }

  @LoginCheck
  @PatchMapping("/{room-id}")
  public ApplicationResponse<Void> update(@LoginHostId Long hostId,
      @PathVariable("room-id") Long roomId,
      @RequestBody RoomRequest request) {
    roomService.patch(hostId, roomId, request);

    return ApplicationResponse.noData(SUCCESS);
  }

  @LoginCheck
  @DeleteMapping("/{room-id}")
  public ApplicationResponse<Void> delete(@LoginHostId Long hostId,
      @PathVariable("room-id") Long roomId) {
    roomService.delete(hostId, roomId);

    return ApplicationResponse.noData(SUCCESS);
  }
}
