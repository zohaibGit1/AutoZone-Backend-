package com.Vechile_Service.controller;

import com.Vechile_Service.dto.ComplaintRequestDto;
import com.Vechile_Service.dto.ComplaintResponseDto;
import com.Vechile_Service.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/complaint")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/register/complaint")
    public ResponseEntity<ComplaintResponseDto> registerComplaint(@RequestBody ComplaintRequestDto complaintRequest) {
        return ResponseEntity.status(201)
                .body(complaintService.registerComplaint(complaintRequest));
    }

}
