package pl.sda.filmrepository;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.sda.filmrepository.dto.CreateSuggestionDTO;

import java.util.Optional;

@Service
public class SuggestionServiceImpl implements SuggestionService {
    private SuggestioRepository suggestioRepository;
    private SubskrypcjaRepo subskrypcjaRepo;
    private MailSender mailSender;

    public SuggestionServiceImpl(MailSender mailSender, SuggestioRepository suggestioRepository, SubskrypcjaRepo subskrypcjaRepo) {
        this.suggestioRepository = suggestioRepository;
        this.subskrypcjaRepo = subskrypcjaRepo;
        this.mailSender = mailSender;
    }

    @Override
    public Suggestion addSuggestion(CreateSuggestionDTO createSuggestionDTO) {
        Suggestion suggestion = new Suggestion();
        suggestion.setLink(createSuggestionDTO.getLink());
        suggestion.setTitle(createSuggestionDTO.getTitle());
        suggestion.setScore(createSuggestionDTO.getScore());
        suggestion.setAuthor(SecurityContextHolder.getContext().getAuthentication().getName());
        notifyAllSubscribers(suggestion);
        return suggestioRepository.save(suggestion);
    }

    private void notifyAllSubscribers(Suggestion suggestion) {
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Iterable<Subskrypcja> all = subskrypcjaRepo.findAll();
                for (Subskrypcja sub : all) {
                    sendMail(sub.getMail(), suggestion);
                }
            }
        });

        myThread.start();
    }

    void sendMail(String mail, Suggestion suggestion) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("A new exciting suggestion for you");
        message.setTo(mail);
        message.setText(String.format("User %s suggests to watch movie %s on %s.", suggestion.getAuthor(), suggestion.getTitle(), suggestion.getLink()));
        mailSender.send(message);
        System.out.printf("Sendimg mail notification to %s", mail);
    }

    @Override
    public Iterable<Suggestion> findSuggestionByAuthor(String author) {
        return suggestioRepository.findByAuthor(author);
    }

    @Override
    public Optional<Suggestion> findById(Long id) {
        return suggestioRepository.findById(id);
    }

    @Override
    public void deleteSuggestionById(Long id) {
        suggestioRepository.deleteById(id);
    }

    @Override
    public Iterable<Suggestion> getAllSuggestions() {
        return suggestioRepository.findAll();
    }
}
