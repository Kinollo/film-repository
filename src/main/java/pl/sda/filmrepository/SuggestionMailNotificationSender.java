package pl.sda.filmrepository;

import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SuggestionMailNotificationSender {

    private SubskrypcjaRepo subskrypcjaRepo;
    private MailSender mailSender;

    public SuggestionMailNotificationSender(SubskrypcjaRepo subskrypcjaRepo, MailSender mailSender) {
        this.subskrypcjaRepo = subskrypcjaRepo;
        this.mailSender = mailSender;
    }

    @EventListener
    @Async
    public void sendMailNotification(SuggestionCreatedEvent event) {
        notifyAllSubscribers(event.getCreatedSuggestion());
    }

    private void notifyAllSubscribers(Suggestion suggestion) {
        Iterable<Subskrypcja> all = subskrypcjaRepo.findAll();
        for (Subskrypcja sub : all) {
            sendMail(sub.getMail(), suggestion);
        }
    }

    void sendMail(String mail, Suggestion suggestion) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("A new exciting suggestion for you");
        message.setTo(mail);
        message.setText(String.format("User %s suggests to watch movie %s on %s.", suggestion.getAuthor(), suggestion.getTitle(), suggestion.getLink()));
        mailSender.send(message);
    }
}
