package com.festapp.FestApplication.service;

import java.util.Optional;

import com.festapp.FestApplication.models.Post;
import com.festapp.FestApplication.models.Reaction;
import com.festapp.FestApplication.models.Reaction.ReactionType;
import com.festapp.FestApplication.models.User;

public interface ReactionService {
	
    Reaction getReactionById(Long id);


	// Metod za dodavanje reakcije na post
	Reaction addReaction(ReactionType type, User user, Post post);

	// Metod za proveru da li korisnik već ima reakciju na post
	Optional<Reaction> getReactionByUserAndPost(User user, Post post);

	// Metod za uklanjanje reakcije korisnika na post
	void removeReaction(Reaction reaction);
}