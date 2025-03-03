package br.com.vitorcsouza.portfolio.service;

import br.com.vitorcsouza.portfolio.model.EmailMessage;
import br.com.vitorcsouza.portfolio.repository.EmailMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableAsync
@Service
public class EmailMessageService {
    private static final Logger logger = LoggerFactory.getLogger(EmailMessageService.class);

    @Autowired
    private EmailMessageRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    public EmailMessage addMessage(String name, String email, String subject, String message) {
        logger.info("Adicionando mensagem para: {}", email);
        EmailMessage emailMessage = new EmailMessage(name, email, subject, message);
        repository.save(emailMessage);
        logger.info("Mensagem salva com sucesso para: {}", email);

        sendEmail(emailMessage);

        return emailMessage;
    }

    public List<EmailMessage> messages() {
        logger.info("Buscando todas as mensagens...");
        List<EmailMessage> allMessages = repository.findAll();
        logger.info("{} mensagens encontradas", allMessages.size());
        return allMessages;
    }

    @Async
    private void sendEmail(EmailMessage message) {
        try {
            logger.info("Enviando e-mail para: {}", message.getEmail());
            var toMe = new SimpleMailMessage();
            toMe.setFrom(message.getEmail());
            toMe.setTo("vitorcavalcantesouza14@gmail.com");
            toMe.setSubject("Mensagem do Portfólio de " + ((message.getSubject() == null) ? message.getName() : message.getSubject()));
            toMe.setText("De: " + message.getName() + "\nFrom: " + message.getEmail() + "\n\nMessage: " + message.getMessage());

            mailSender.send(toMe);
            logger.info("E-mail enviado com sucesso de: {}", message.getEmail());

            var toBack = new SimpleMailMessage();
            toBack.setFrom("vitorcavalcantesouza14@gmail.com");
            toBack.setTo(message.getEmail());
            toBack.setSubject("Recebemos sua mensagem!");
            String messageReturn = String.format("""             
                    Olá, %s!
                    
                    Agradeço por entrar em contato através do meu portfólio.
                    Recebi sua mensagem e em breve retornarei com uma resposta.
                    
                    Caso queira falar comigo diretamente, sinta-se à vontade para me chamar no WhatsApp pelo botão disponível no site.
                    
                    Até logo!
                    
                    Atenciosamente,
                    Vítor Cavalcante Souza
                    """, message.getName());
            toBack.setText(messageReturn);
            mailSender.send(toBack);
        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail de: {} - Erro: {}", message.getEmail(), e.getMessage(), e);
        }
    }
}
