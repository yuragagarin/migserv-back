package ru.migplus.site.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "surname", length = 30)
    private String surname;

    @Column(name = "patronymic", length = 30)
    private String patronymic;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_post_id", foreignKey = @ForeignKey(name = "fk_user_post"))
    private UserPost post;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @JsonIgnoreProperties("users")
    private List<Role> roles;

    @JsonBackReference
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "consumer_id", foreignKey = @ForeignKey(name = "fk_consumer"))
    private Consumer consumer;

    @Column(name = "updated", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(10) default 'ACTIVE'")
    private Status status = Status.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user"))
    private User user;

    /*@Builder
    public User(String username, String password, String email, List<Role> roles, Status status){
        this.username = username;
        this.password = password;
        this.email = email;
        this.setStatus(status);
    }*/
}
