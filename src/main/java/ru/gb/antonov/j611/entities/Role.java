package ru.gb.antonov.j611.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table (name="roles")
public class Role
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;

    @NotNull (message="Задайте имя роли в формате: ROLE_ИМЯРОЛИ (6…64 латин.символов, включая обязат.префикс ROLE_).\r")
    @Length (min=6, max=64, message="Длина названия — 6…64 латин.символов.\r")
    @Column (name="name", nullable=false, unique=true)
    private String name;
}
