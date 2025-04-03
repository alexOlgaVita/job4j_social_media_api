package ru.job4j.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

@Schema(description = "User Model Information")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Name title", example = "Mediator")
    @NotBlank(message = "Name не может быть пустым")
    @Length(min = 4,
            max = 15,
            message = "username должно быть не менее 4 и не более 15 символов")
    private String name;

    @Schema(description = "email of User", example = "harry@mail.com")
    @Pattern(regexp = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}",
            message = "email должен соответствовать паттерну почтового адреса")
    private String email;

    @Schema(description = "password of User", example = "pAssword_$#")
    @NotBlank(message = "password не может быть пустым")
    @Length(min = 5,
            max = 15,
            message = "password должно быть не менее 5 и не более 15 символов")
    private String password;

    /* @ManyToMany(mappedBy = "participates", fetch = FetchType.LAZY) */
    /* альтернатива маппед: обычная */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "participates", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "post_id", nullable = false, updatable = false, insertable = false)})
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends",
            joinColumns = {@JoinColumn(name = "user_id1")},
            inverseJoinColumns = {@JoinColumn(name = "user_id2")}
    )
    private Set<User> friends = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friendLoans",
            joinColumns = {@JoinColumn(name = "user_id1")},
            inverseJoinColumns = {@JoinColumn(name = "user_id2")}
    )
    private Set<User> potentialFriends = new HashSet<>();

    @Column(name = "user_zone")
    private String timezone = TimeZone.getDefault().getDisplayName();
}