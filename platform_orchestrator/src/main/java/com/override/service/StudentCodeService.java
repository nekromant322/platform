package com.override.service;

import com.override.models.PlatformUser;
import com.override.models.StudentCode;
import com.override.repositories.StudentCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCodeService {
    @Autowired
    private StudentCodeRepository studentCodeRepository;

    public List<StudentCode> findAllStudentCodes(PlatformUser user){
        return studentCodeRepository.findAllByUser(user);
    }
}
