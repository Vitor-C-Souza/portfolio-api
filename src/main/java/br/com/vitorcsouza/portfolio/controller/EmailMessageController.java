package br.com.vitorcsouza.portfolio.controller;

import br.com.vitorcsouza.portfolio.model.EmailMessage;
import br.com.vitorcsouza.portfolio.service.EmailMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EmailMessageController {
    @Autowired
    private EmailMessageService service;

    @MutationMapping
    public EmailMessage sendMessage(@Argument String name, @Argument String email, @Argument String subject, @Argument String message) {
        return service.addMessage(name, email, subject, message);
    }

    @QueryMapping
    public List<EmailMessage> messages() {
        return service.messages();
    }
}
