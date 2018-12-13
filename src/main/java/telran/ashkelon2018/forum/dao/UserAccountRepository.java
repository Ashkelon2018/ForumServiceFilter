package telran.ashkelon2018.forum.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.forum.domain.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

}
