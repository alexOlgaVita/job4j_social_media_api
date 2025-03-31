package ru.job4j.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.name = :name, p.description = :description where p.id = :id")
    int updatePostByNameAndDescription(@Param("id") Long id,
                                       @Param("name") String name,
                                       @Param("description") String description);

    @Query("from Post as u INNER JOIN FETCH u.photos where u.id = :id")
    Optional<Post> findAllPhotosByPostId(@Param("id") Long id);

    @Query("from Post u INNER JOIN FETCH u.photos")
    List<Post> findPostWithPhotos();

    @Query("from Post")
    @EntityGraph(attributePaths = {"photos"})
    List<Post> findAll();

    @Query("select pp from Post as pp"
            + " WHERE pp.user in (select u from User as u WHERE u.name = :name and u.password = :password"
            + " )"
    )
    List<Post> findPostsByUser(@Param("name") String name, @Param("password") String password);

    @Query("from Post as u where u.id = :id")
    @EntityGraph(attributePaths = {"photos"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Post> findAllPhotosByPostId2(@Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Post p where p.id = :id")
    int delete(@Param("id") Long id);

    /**
     * Список всех постов подписчиков пользователя от сортированных от самых новых к старым с пагинацией.
     *
     * @return
     */

    @Query("select p, s from Post as p, User as s"
            + " JOIN FETCH p.participates f"
            + " JOIN FETCH s.potentialFriends pf"
            + " WHERE"
            + " pf = f"
    )
    List<Post> findAllSubSubscribersPosts2(@Param("name") String name, @Param("password") String password);

    /**
     * Список всех постов подписчиков пользователя от сортированных от самых новых к старым с пагинацией.
     */
    @Query("select pp from Post as pp"
            + " WHERE pp.user in (select u.potentialFriends from User as u WHERE u.name = :name and u.password = :password"
            + " )"
    )
    List<Post> findAllSubSubscribersPosts(@Param("name") String name, @Param("password") String password);

    @Transactional
    @Modifying
    @Query("""
            update Post p
            set p.name = :#{#post.name},
            p.description = :#{#post.description},
            p.created = :#{#post.created},
            p.user = :#{#post.user},
            p.photos = :#{#post.photos},
            p.participates = :#{#post.photos}
            where p.id=:#{#post.id}
            """)
    int update(@Param("post") Post post);
}