package ru.job4j.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

   /* @ManyToMany(mappedBy = "participates", fetch = FetchType.LAZY) */
    /* альтернатива маппед: обычная */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "participates", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "post_id", nullable = false, updatable = false, insertable = false)})
    private Set<Post> posts = new HashSet<>();

/*
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends",
            joinColumns = {@JoinColumn(name = "user_id1")},
            inverseJoinColumns = {@JoinColumn(name = "user_id2")}
    )
    private Set<User> friends = new HashSet<>();
 */
/*
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friendLoans",
            joinColumns = {@JoinColumn(name = "user_id1")},
            inverseJoinColumns = {@JoinColumn(name = "user_id2")}
    )
    private Set<User> potentialFriends = new HashSet<>();
*/
    @Column(name = "user_zone")
    private String timezone = TimeZone.getDefault().getDisplayName();
}