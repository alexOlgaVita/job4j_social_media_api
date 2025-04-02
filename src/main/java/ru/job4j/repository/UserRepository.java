package ru.job4j.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from User as u where u.name = :name and u.password = :password")
    Optional<User> findByLoginPassword(@Param("name") String name, @Param("password") String password);

    @Query("select f, u from User as u"
            + " JOIN FETCH u.potentialFriends f"
            + " WHERE u.name = :name and u.password = :password")
    List<User> findAllSubscribers(@Param("name") String name, @Param("password") String password);

    @Query("select f, u from User as u"
            + " JOIN FETCH u.friends f"
            + " WHERE u.name = :name and u.password = :password")
    List<User> findAllFriends(@Param("name") String name, @Param("password") String password);

    @Transactional
    @Modifying
    @Query("""
            update User u
            set u.name = :#{#user.name},
            u.email = :#{#user.email},
            u.password = :#{#user.password},
            u.posts = :#{#user.posts},
            u.friends = :#{#user.friends},
            u.potentialFriends = :#{#user.potentialFriends},
            u.timezone = :#{#user.timezone}
            where u.id=:#{#user.id}
            """)
    int update(@Param("user") User user);

    @Query("from User as u where u.id = :id")
    Optional<User> findById(@Param("id") Long id);

    @Modifying
    @Query("delete from User u where u.id=:pId")
    int delete(@Param("pId") Long id);

    @Query("from User")
    List<User> findAll();
}