package com.example.streamusserver.config;

import com.example.streamusserver.dto.SendEmailRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.naming.NamingException;
import java.util.regex.Pattern;

import static javax.naming.InitialContext.doLookup;


@RequiredArgsConstructor
@Configuration
@Slf4j
public class EmailConfig {
    private final JavaMailSender mailSender;
    private static final Marker IMPORTANT = MarkerFactory.getMarker("IMPORTANT");


    public boolean isValidEmail(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    public void isEmailDomainValid(String email) {
        try {
            String domain = email.substring(email.lastIndexOf('@') + 1);
            int result = doLookup(domain);
            log.info("Domain: " + domain);
            log.info("Result of domain: " + result);
        } catch (NamingException e) {
            log.error(e.getMessage());
        }
    }

    public ResponseEntity<String> sendEmail(SendEmailRequestDto request) {

        if (!isValidEmail(request.getReceiver()))
            new ResponseEntity<>("Email is not valid", HttpStatus.BAD_REQUEST);

        isEmailDomainValid(request.getReceiver());

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setFrom("${SPRING_MAIL_USERNAME}");
            message.setTo(request.getReceiver());
            message.setSubject(request.getSubject());
            message.setText(request.getBody(), true);
        };

        try {
            log.info("Beginning of log *********");
            log.info(IMPORTANT, "Sending mail to: " + request.getReceiver());
            mailSender.send(messagePreparator);
            return new ResponseEntity<>("Sent", HttpStatus.OK);
        } catch (Exception e) {
            log.error(IMPORTANT, e.getMessage());
        }

        return new ResponseEntity<>("An Error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

