package com.email.email.writer.emailController;

import com.email.email.writer.emailService.EmailGeneratorService;
import com.email.email.writer.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*",
        allowedHeaders = "*",
        methods = { RequestMethod.GET, RequestMethod.POST}
)
public class EmailGeneratorController {
    @Autowired
    private EmailGeneratorService emailGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateEmail(@RequestBody EmailRequest emailRequest){
        String response = emailGeneratorService.generateEmailReply(emailRequest);
        return ResponseEntity.ok(response);
    }
}
