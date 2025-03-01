package br.com.vitorcsouza.portfolio.service;

import br.com.vitorcsouza.portfolio.model.EmailMessage;
import br.com.vitorcsouza.portfolio.repository.EmailMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailMessageService {
    @Autowired
    private EmailMessageRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    public EmailMessage addMessage(String name, String email, String subject, String message) {
        EmailMessage emailMessage = new EmailMessage(name, email, subject, message);


        SimpleMailMessage toMe = new SimpleMailMessage();
        toMe.setFrom(email);
        toMe.setTo("vitorcavalcantesouza14@gmail.com");
        toMe.setSubject("Mensagem do Portfólio de " + ((subject == null) ? name : subject));
        toMe.setText("De: " + email + "\n\n" + message);
        mailSender.send(toMe);
        System.out.println("email enviado!!!");

        repository.save(emailMessage);
        return emailMessage;
    }

    public List<EmailMessage> messages() {
        return repository.findAll();
    }
}
