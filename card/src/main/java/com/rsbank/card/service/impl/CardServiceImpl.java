package com.rsbank.card.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.rsbank.card.constants.CustomStatus;
import com.rsbank.card.constants.Literals;
import com.rsbank.card.dto.CardDto;
import com.rsbank.card.entity.Card;
import com.rsbank.card.exception.CardAlreadyExistsException;
import com.rsbank.card.exception.ResourceNotFoundException;
import com.rsbank.card.mapper.CardMapper;
import com.rsbank.card.repository.CardRepository;
import com.rsbank.card.service.ICardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements ICardService {

    private final CardRepository cardRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCard= cardRepository.findByMobileNumber(mobileNumber);
        if(optionalCard.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardRepository.save(createNewCard(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(Literals.CREDIT_CARD);
        newCard.setTotalLimit(Literals.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(Literals.NEW_CARD_LIMIT);
        return newCard;
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Card", "mobileNumber", mobileNumber))
        );
        return CardMapper.mapToCardDto(card, new CardDto());
    }

    /**
     *
     * @param cardDto - CardDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardDto cardDto) {
        Card card = cardRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Card", "cardNumber", cardDto.getCardNumber())));
        CardMapper.mapToCard(cardDto, card);
        cardRepository.save(card);
        return  true;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Card", "mobileNumber", mobileNumber))
        );
        cardRepository.deleteById(card.getCardId());
        return true;
    }
    
}
