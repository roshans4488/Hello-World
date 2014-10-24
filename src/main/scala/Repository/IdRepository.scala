package Wallet.Repository




import Wallet.Resource.IDCard
import java.util.List
import java.util.ArrayList
import org.springframework.data.mongodb.repository.MongoRepository

trait IdRepository extends MongoRepository[IDCard, String] {

    def findByUserId(userId:Int):ArrayList[IDCard];
    def findByCardId(card_id:Int):IDCard;
    

}