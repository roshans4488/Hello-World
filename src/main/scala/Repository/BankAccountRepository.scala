package Wallet.Repository


import Wallet.Resource.BankAccount
import java.util.List
import java.util.ArrayList
import org.springframework.data.mongodb.repository.MongoRepository

trait BankAccountRepository extends MongoRepository[BankAccount, String] {

    def findByUserId(userId:Int):ArrayList[BankAccount];
    def findByBaId(ba_id:Int):BankAccount;
    

}