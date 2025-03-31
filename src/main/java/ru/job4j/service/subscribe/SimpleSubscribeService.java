package ru.job4j.service.subscribe;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.job4j.model.Post;
import ru.job4j.model.User;
import ru.job4j.repository.PostRepository;
import ru.job4j.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class SimpleSubscribeService implements SubscribeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public SimpleSubscribeService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    /**
     * Отправка заявки на добавление в друзья.
     * С этого момента, пользователь, отправивший заявку, остается подписчиком до тех пор, пока сам не откажется от подписки.
     * Если пользователь, получивший заявку, принимает ее, оба пользователя становятся друзьями.
     * Если отклонит, то пользователь, отправивший заявку, как и указано ранее, все равно остается подписчиком.
     *
     * @param userTo   Пользователь, которому отправляется заявка
     * @param userFrom Пользователь, который отправил заявку
     */
    @Transactional
    @Override
    public void createFriendLoan(User userTo, User userFrom) {
        Set<User> potentialFriendsNew = userTo.getPotentialFriends();
        potentialFriendsNew.add(userFrom);
        userTo.setPotentialFriends(potentialFriendsNew);
        userRepository.save(userTo);

        List<Post> allPosts = postRepository.findPostsByUser(userTo.getName(), userTo.getPassword());
        for (Post post : allPosts) {
            var participatesNew = post.getParticipates();
            participatesNew.add(userFrom);
            postRepository.save(post);
        }
    }

    /**
     * Подтверждение заявки в друзья.
     * Пользователи, являющиеся друзьями, также являются подписчиками друг на друга.
     *
     * @param userTo пользователь, который подтверждает заявку в друзья
     * @param userFrom пользователь, который становится другом
     */
    @Transactional
    @Override
    public void acceptFriend(User userTo, User userFrom) {
        Set<User> friendsNew = userTo.getFriends();
        friendsNew.add(userFrom);
        userTo.setFriends(friendsNew);
        userRepository.save(userTo);

        List<Post> allPosts = postRepository.findPostsByUser(userFrom.getName(), userFrom.getPassword());
        for (Post post : allPosts) {
            var participatesNew = post.getParticipates();
            participatesNew.add(userTo);
            postRepository.save(post);
        }
    }

    /**
     * Удаление пользователя из друзей.
     * Если один из друзей удаляет другого из друзей, то он также отписывается.
     * Второй пользователь остается подписчиком.
     *
     * @param userOwn    пользователь, который удаляет из друзей
     * @param userFriend удаляемый из друзей пользователь
     */
    @Transactional
    @Override
    public void deleteFriend(User userOwn, User userFriend) {
        Set<User> friendsNew = userOwn.getFriends();
        friendsNew.remove(userFriend);
        userOwn.setFriends(friendsNew);
        userRepository.save(userOwn);

        List<Post> allPosts = postRepository.findPostsByUser(userFriend.getName(), userFriend.getPassword());
        for (Post post : allPosts) {
            var participatesNew = post.getParticipates();
            participatesNew.remove(userOwn);
            postRepository.save(post);
        }
    }
}
