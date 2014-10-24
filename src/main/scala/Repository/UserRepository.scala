package Wallet.Repository




import Wallet.Resource.User
import java.util.List

import org.springframework.data.mongodb.repository.MongoRepository

trait UserRepository extends MongoRepository[User, String] {

    def findByUserId(userId:Int):User;
    

}