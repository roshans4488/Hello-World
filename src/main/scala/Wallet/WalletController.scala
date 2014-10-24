package Wallet


import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.{PathVariable,  RequestMethod,RequestParam, RequestMapping,RequestBody,ResponseBody,ResponseStatus,RequestHeader}

import org.springframework.http.HttpStatus
import Resource.User
import Resource.IDCard
import Resource.WebLogin
import Resource.BankAccount

import Exception.ResourceNotFoundException
import org.springframework.stereotype.Controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid

import java.util.concurrent.atomic.AtomicInteger;
import Wallet.Repository.UserRepository
import Wallet.Repository.IdRepository
import Wallet.Repository.WebLoginRepository
import Wallet.Repository.BankAccountRepository
import java.util.Map

import java.util.Arrays;

import org.springframework.http._

import java.util.Date
import java.util.Collections
import java.text.SimpleDateFormat

import org.json.simple.JSONObject;


import org.json.simple.parser.JSONParser;
import org.springframework.data.domain.Sort

import com.mongodb.DB;
import com.mongodb.MongoClient;


import java.util.ArrayList
import org.springframework.web.client.RestTemplate
@EnableAutoConfiguration
@RestController
@RequestMapping(value = Array("/api/v1"))
class  WalletConfig @Autowired() (private val userRepository: UserRepository, private val idRepository: IdRepository, private val webLoginRepository:WebLoginRepository,private val bankAccountRepository:BankAccountRepository)  {


 
 private final  var Usercounter: AtomicInteger = new AtomicInteger();
 private final  var IDcounter: AtomicInteger = new AtomicInteger();
private  final  var WebLogincounter: AtomicInteger = new AtomicInteger();
private  final  var BankAccountcounter: AtomicInteger = new AtomicInteger();

 
 //------------------------------------USER---------------------------------------------------------------------------------------

@RequestMapping(value = Array(""),method = Array(RequestMethod.GET))
@ResponseBody
def greet():String = {
  return "Hello! Welcome to Digital Wallet app."

}


 
@RequestMapping(value = Array("/users/{user_id}"),method = Array(RequestMethod.GET), produces = Array("application/json"))
@ResponseBody
def listUsers(@PathVariable("user_id") user_id:Int):User = {
  if (userRepository.findByUserId(user_id)==null) 
    {  
      
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }
  else 
  {

  
  return userRepository.findByUserId(user_id)

}
}




 @RequestMapping(value = Array("/users") ,method = Array(RequestMethod.POST), consumes =
    Array("application/json") , produces = Array("application/json")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    def createUser(@RequestBody @Valid User: User): User = {
       



    var df:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");



val UserList = userRepository.findAll(new Sort(Sort.Direction.DESC, "userId"))
var user_id: Int = 0
if(!UserList.isEmpty())
  user_id = UserList.get(0).getUserId()+1


else
  user_id = 1


       User.setUserId ( user_id)
       User.setCreated_at( df.format(new Date()) )
      
      userRepository.save(User);
       return User
}



@RequestMapping(value = Array("/users/{user_id}") ,method = Array(RequestMethod.PUT), consumes =
    Array("application/json") , produces = Array("application/json")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    def updateUser(@RequestBody User: User,@PathVariable("user_id") user_id:Int): User = {
       
 if (userRepository.findByUserId(user_id)==null) 
    {  
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }

     else{  
     
       var df:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
       
      
 User.setUserId ( user_id)
  User.setCreated_at( userRepository.findByUserId(user_id).getCreated_at())
User.setUpdated_at( df.format(new Date()) )


        userRepository.save(User);
       return userRepository.findByUserId(user_id)
     
     }
}


//------------------------------------IDCARD---------------------------------------------------------------------------------------

@RequestMapping(value = Array("/users/{user_id}/idcards") ,method = Array(RequestMethod.POST), consumes =
    Array("application/json") , produces = Array("application/json")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    def createCard(@RequestBody @Valid IDCard: IDCard,@PathVariable("user_id") user_id:Int): IDCard = {
       

if (userRepository.findByUserId(user_id)==null) 
    {  
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }


else{



val IDCardList = idRepository.findAll(new Sort(Sort.Direction.DESC, "cardId"))
var card_id: Int = 0
if(!IDCardList.isEmpty())
  card_id = IDCardList.get(0).getCardId()+1


else
  card_id = 1

      IDCard.setCardId ( card_id)
       IDCard.setUserId ( user_id)
    

      idRepository.save(IDCard);
       return IDCard

     }
}




@RequestMapping(value = Array("/users/{user_id}/idcards"),method = Array(RequestMethod.GET), produces = Array("application/json"))
@ResponseBody
def listCards(@PathVariable("user_id") user_id:Int):ArrayList[IDCard] = {

  if (userRepository.findByUserId(user_id)==null) 
    {  
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }

 
else if (idRepository.findByUserId(user_id)==null) 
    {  
     
      throw new ResourceNotFoundException("No ID Card exists for user "+user_id.toString())
    }



else{
return idRepository.findByUserId(user_id)
}
}


@RequestMapping(value = Array("/users/{user_id}/idcards/{card_id}"),method = Array(RequestMethod.DELETE), produces = Array("application/json"))
@ResponseStatus(HttpStatus.NO_CONTENT)
@ResponseBody
def deleteCard(@PathVariable("user_id") user_id:Int,@PathVariable("card_id") card_id:Int) = {

if (userRepository.findByUserId(user_id)==null) 
    {  
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }


else if (  (idRepository.findByUserId(user_id)==null) || (idRepository.findByCardId(card_id)==null) || (idRepository.findByCardId(card_id).getUserId()!= user_id) ) 
    {  
     
      throw new ResourceNotFoundException("ID Card "+card_id+" doesnt exist for user "+user_id.toString())
    }

else
{
 

idRepository.delete(idRepository.findByCardId(card_id))

}
}
//------------------------------------WEBLOGIN--------------------------------------------------------------------------------

@RequestMapping(value = Array("/users/{user_id}/weblogins") ,method = Array(RequestMethod.POST), consumes =
    Array("application/json") , produces = Array("application/json")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    def createWebLogin(@RequestBody @Valid WebLogin: WebLogin,@PathVariable("user_id") user_id:Int): WebLogin = {
       
if (userRepository.findByUserId(user_id)==null) 
    {  
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }

else{


val WebLoginList = webLoginRepository.findAll(new Sort(Sort.Direction.DESC, "loginId"))
var login_id: Int = 0
if(!WebLoginList.isEmpty())
  login_id = WebLoginList.get(0).getLoginId()+1


else
  login_id = 1

      WebLogin.setLoginId( login_id) 
      WebLogin.setUserId ( user_id)
      webLoginRepository.save(WebLogin);
       return WebLogin

     }
}


@RequestMapping(value = Array("/users/{user_id}/weblogins"),method = Array(RequestMethod.GET), produces = Array("application/json"))
@ResponseBody
def listWebLogins(@PathVariable("user_id") user_id:Int):ArrayList[WebLogin] = {

if (userRepository.findByUserId(user_id)==null) 
    {  
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }


else if (webLoginRepository.findByUserId(user_id)==null) 
    { 
      throw new ResourceNotFoundException("No web login exists for user "+user_id.toString())
    }
    else{
      
  return webLoginRepository.findByUserId(user_id)
}
}




@RequestMapping(value = Array("/users/{user_id}/weblogins/{login_id}"),method = Array(RequestMethod.DELETE), produces = Array("application/json"))
@ResponseStatus(HttpStatus.NO_CONTENT)
@ResponseBody
def deleteWebLogin(@PathVariable("user_id") user_id:Int,@PathVariable("login_id") login_id:Int) = {

if (userRepository.findByUserId(user_id)==null) 
    {  
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }


else if ((webLoginRepository.findByUserId(user_id)==null) || (webLoginRepository.findByLoginId(login_id)==null) || (webLoginRepository.findByLoginId(login_id).getUserId()!= user_id)) 
    { 
      throw new ResourceNotFoundException("Web login "+login_id+" doesnt exist for user "+user_id.toString())
    }

else{
 
  webLoginRepository.delete(webLoginRepository.findByLoginId(login_id))
}
}




//------------------------------------BANK_ACCOUNT--------------------------------------------------------------------------------

@RequestMapping(value = Array("/users/{user_id}/bankaccounts") ,method = Array(RequestMethod.POST), consumes =
    Array("application/json") , produces = Array("application/json")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    def createBankAccount(@RequestBody @Valid BankAccount: BankAccount,@PathVariable("user_id") user_id:Int): BankAccount = {
       
if (userRepository.findByUserId(user_id)==null) 
    {  
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }

     else{  
     

     var template:RestTemplate= new RestTemplate()
val result:String  = template.getForObject("http://www.routingnumbers.info/api/data.json?rn="+BankAccount.getRouting_number(), classOf[String])
 var parser:JSONParser=new JSONParser();
 val jsonObject = parser.parse(result).asInstanceOf[JSONObject]

    

val code = jsonObject.get("code")
   

if(code == 200)
{



val BankAccountList = bankAccountRepository.findAll(new Sort(Sort.Direction.DESC, "baId"))
var ba_id: Int = 0
if(!BankAccountList.isEmpty())
  ba_id = BankAccountList.get(0).getBaId()+1


else
  ba_id = 1

 BankAccount.setBaId (ba_id)
BankAccount.setUserId(user_id)
val customer_name = jsonObject.get("customer_name").asInstanceOf[String]
BankAccount.setAccount_name(customer_name)
  bankAccountRepository.save(BankAccount);
       return BankAccount
}


 
 else
 {

val message = jsonObject.get("message").asInstanceOf[String]
   
if(message == "not found")
   throw new ResourceNotFoundException("Routing number "+message)
 else
  throw new ResourceNotFoundException(message)


 }

      

     }
}






@RequestMapping(value = Array("/users/{user_id}/bankaccounts"),method = Array(RequestMethod.GET), produces = Array("application/json"))
@ResponseBody
def listBankAccounts(@PathVariable("user_id") user_id:Int):ArrayList[BankAccount] = {

  if (userRepository.findByUserId(user_id)==null) 
    {  
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }

else if (bankAccountRepository.findByUserId(user_id)==null) 
    { 
      throw new ResourceNotFoundException("No bank account exists for user "+user_id.toString())
    }

else{
  
return bankAccountRepository.findByUserId(user_id)
}
}



@RequestMapping(value = Array("/users/{user_id}/bankaccounts/{ba_id}"),method = Array(RequestMethod.DELETE), produces = Array("application/json"))
@ResponseStatus(HttpStatus.NO_CONTENT)
@ResponseBody
def deleteBankAccount(@PathVariable("user_id") user_id:Int,@PathVariable("ba_id") ba_id:Int) = {

if (userRepository.findByUserId(user_id)==null) 
    {  
      throw new ResourceNotFoundException("UserId "+user_id.toString()+" doesnt exist in the system.")
    }



else if ((bankAccountRepository.findByUserId(user_id)==null) || (bankAccountRepository.findByBaId(ba_id)==null) || (bankAccountRepository.findByBaId(ba_id).getUserId()!= user_id)) 
    { 
      throw new ResourceNotFoundException("Bank account "+ba_id+" doesnt exist for user "+user_id.toString())
    }

else{
  bankAccountRepository.delete(bankAccountRepository.findByBaId(ba_id))
}
}





}
