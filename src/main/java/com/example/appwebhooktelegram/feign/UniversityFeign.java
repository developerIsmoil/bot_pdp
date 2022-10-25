package com.example.appwebhooktelegram.feign;

import com.example.appwebhooktelegram.payload.*;
import com.example.appwebhooktelegram.results.ApiResult;
import com.example.appwebhooktelegram.results.ApiResultDTO;
import com.example.appwebhooktelegram.utils.RestConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@FeignClient(url = RestConstants.UNIVERSITY_BASE_PATH, name = "UNIVERSITY-FEIGN")
public interface UniversityFeign {
    @GetMapping(RestConstants.BAR_CODE_ATTACHMENT_PATH + "/{phoneNumber}")
    HttpEntity<byte[]> getAttachment(@PathVariable String phoneNumber);

    @PostMapping(RestConstants.CHECK_REGISTERED)
    HttpEntity<CheckNumberDTO> checkRegister(@RequestBody RegisterDTO registerDTO);

    @PostMapping(RestConstants.CHECK_REGISTERED)
    HttpEntity<?> check(@RequestBody RegisterDTO registerDTO);

    //PASSWORD VA PHONE_NUMBER BILAN RO'YXATDAN O'TISH UCHUN SMS CODE BERADI
    @PostMapping(RestConstants.SET_PASSWORD_SEND_SMS_URL)
    HttpEntity<SendSmsCodeDTO> setPassword(@RequestBody RegisterDTO registerDTO);

    @PostMapping(RestConstants.CHECK_PASSWORD_URL)
    HttpEntity<RegisterResultDTO> checkSmsCode(@RequestBody CheckSmsDTO checkSmsDTO);

    @GetMapping(RestConstants.SEARCH_BY_PHONE_NUMBER_PATH)
    ApiResult<UserDTO> searchByPhoneNumber(@RequestParam String phoneNumber);

    @GetMapping(RestConstants.GET_INFO_USER + "/{id}")
    SendUser searchById(@PathVariable UUID id);

    @GetMapping(RestConstants.UNIVERSITY_EXAMS_BOTS_API)
    ApiResult<HashMap<String, List<ExamDTO>>> getExams();

}
