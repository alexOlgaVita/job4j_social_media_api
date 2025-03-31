package ru.job4j.service.subscribe;

import ru.job4j.model.User;

public interface SubscribeService {

    void deleteFriend(User userTo, User userFrom);

    void createFriendLoan(User userTo, User userFrom);

    void acceptFriend(User userTo, User userFrom);
}