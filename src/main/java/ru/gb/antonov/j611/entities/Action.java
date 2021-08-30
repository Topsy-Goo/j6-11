package ru.gb.antonov.j611.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
@Table (name="actions")
public class Action
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;

    @NotNull (message="Задайте название действия (3…64 латин.символов).\r")
    @Length (min=3, max=32, message="Длина названия действия — 3…64 символов.\r")
    @Column(name="name", nullable=false, unique=true)
    private String name;

/*    @ManyToMany
    @JoinTable (name="ourusers_actions",
                joinColumns        = @JoinColumn (name="action_id"),
                inverseJoinColumns = @JoinColumn (name="user_id"))
    private Collection<OurUser> ourUsers;*/
}
