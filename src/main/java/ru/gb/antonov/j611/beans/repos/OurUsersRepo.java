package ru.gb.antonov.j611.beans.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.antonov.j611.entities.OurUser;

import java.util.Optional;

@Repository
public interface OurUsersRepo extends CrudRepository <OurUser, Long>
{
    Optional<OurUser> findByLogin (String login);
}
