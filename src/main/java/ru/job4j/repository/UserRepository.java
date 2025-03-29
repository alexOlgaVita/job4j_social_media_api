package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from User as u where u.name = :name and u.password = :password")
    Optional<User> findByNameAndPassword(@Param("name") String name, @Param("password") String password);

    @Query("select f, u from User as u"
            + " JOIN FETCH u.potentialFriends f"
            + " WHERE u.name = :name and u.password = :password")
    List<User> findAllSubscribers(@Param("name") String name, @Param("password") String password);

    @Query("select f, u from User as u"
            + " JOIN FETCH u.friends f"
            + " WHERE u.name = :name and u.password = :password")
    List<User> findAllFriends(@Param("name") String name, @Param("password") String password);
}
