package ru.coach.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * https://dzone.com/tutorials/java/hibernate/hibernate-example/hibernate-mapping-many-to-many-1.html
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "simple_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String hashPassword;// храним не сам пароль, а его hash <<

    @Enumerated(value = EnumType.STRING)// persist аннотация, для маппинга в DB
    private Authority authority;

    //TODO: таблица аутентификации по token (для REST)
    //https://stackoverflow.com/questions/287201/how-to-persist-a-property-of-type-liststring-in-jpa
    @ElementCollection// JPA persistence//
    @CollectionTable(name = "token", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "value")
    private List<String> tokens;
}
