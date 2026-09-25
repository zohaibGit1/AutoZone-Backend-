package com.Vechile_Service.service;

import com.Vechile_Service.dto.ComplaintRequestDto;
import com.Vechile_Service.dto.ComplaintResponseDto;

public interface ComplaintService {
    ComplaintResponseDto registerComplaint(ComplaintRequestDto complaintRequest);
}