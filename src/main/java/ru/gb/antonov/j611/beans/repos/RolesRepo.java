package ru.gb.antonov.j611.beans.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.antonov.j611.entities.Role;

@Repository
public interface RolesRepo extends CrudRepository <Role, Integer>  {}
