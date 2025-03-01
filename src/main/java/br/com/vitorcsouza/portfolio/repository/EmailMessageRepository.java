package br.com.vitorcsouza.portfolio.repository;

import br.com.vitorcsouza.portfolio.model.EmailMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailMessageRepository extends MongoRepository<EmailMessage, String> {
}
