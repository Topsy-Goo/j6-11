package ru.gb.antonov.j611.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
@Table(name="ourusers")
public class OurUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;

    @NotNull (message="Задайте логин (3…32 латин.символов).\r")
    @Length (min=3, max=32, message="Длина логина — 3…32 символов.\r")
    @Column(name="login", nullable=false, unique=true)
    private String login;

    @NotNull (message="Задайте пароль (3…64 символов).\r")
    @Length (min=3, max=64, message="Длина пароля — 3…64 символов.\r")
    @Column(name="password", nullable=false)
    private String password;

    @NotNull (message="Задайте адрес электронной почты (до 64 символов).\r")
    @Length (max=64, message="Длина адреса — до 64 символов.\r")
    @Column(name="email", nullable=false, unique=true)
    private String email;

    @ManyToMany
    @JoinTable (name="ourusers_roles",
                joinColumns        = @JoinColumn (name="user_id"),
                inverseJoinColumns = @JoinColumn (name="role_id"))
    private Collection<Role> roles;

    @ManyToMany
    @JoinTable (name="ourusers_actions",
                joinColumns        = @JoinColumn (name="user_id"),
                inverseJoinColumns = @JoinColumn (name="action_id"))
    private Collection<Action> actions;
}
