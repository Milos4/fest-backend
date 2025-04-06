package com.festapp.FestApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.festapp.FestApplication.models.Post;
import com.festapp.FestApplication.models.Reaction;
import com.festapp.FestApplication.models.Reaction.ReactionType;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.repository.ReactionRepository;

import java.util.Optional;

@Service
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    
    
    @Override
    public Reaction getReactionById(Long id) {
        return reactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Reaction not found"));
    }

    @Autowired
    public ReactionServiceImpl(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    @Override
    public Reaction addReaction(ReactionType type, User user, Post post) {
        // Proveravamo da li korisnik već ima reakciju na ovaj post
        Optional<Reaction> existingReaction = getReactionByUserAndPost(user, post);

        // Ako korisnik već ima reakciju, ažuriramo je
        if (existingReaction.isPresent()) {
            Reaction reaction = existingReaction.get();
            reaction.setType(type);
            return reactionRepository.save(reaction);
        }

        // Ako korisnik nema reakciju, kreiramo novu
        Reaction newReaction = new Reaction(type, user, post);
        return reactionRepository.save(newReaction);
    }

    @Override
    public Optional<Reaction> getReactionByUserAndPost(User user, Post post) {
        // Pretražujemo reakciju korisnika na dati post
        return reactionRepository.findByUserAndPost(user, post);
    }

    @Override
    public void removeReaction(Reaction reaction) {
        // Uklanjamo reakciju iz baze podataka
        reactionRepository.delete(reaction);
    }
}
