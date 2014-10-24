package Wallet.Repository


import Wallet.Resource.WebLogin
import java.util.List
import java.util.ArrayList
import org.springframework.data.mongodb.repository.MongoRepository

trait WebLoginRepository extends MongoRepository[WebLogin, String] {

    def findByUserId(userId:Int):ArrayList[WebLogin];
    def findByLoginId(card_id:Int):WebLogin;
    

}