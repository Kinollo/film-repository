package pl.sda.filmrepository.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.filmrepository.model.Subskrypcja;
import pl.sda.filmrepository.repository.SubskrypcjaRepo;

@RestController
@RequestMapping("/api/subscribers")
public class SubscribeController {
    SubskrypcjaRepo subskrypcjaRepo;

    public SubscribeController(SubskrypcjaRepo subskrypcjaRepo) {
        this.subskrypcjaRepo = subskrypcjaRepo;
    }

    @PostMapping
    void addSubscriber (@RequestBody Subskrypcja subskrypcja){
        subskrypcjaRepo.save(subskrypcja);
    }

}
