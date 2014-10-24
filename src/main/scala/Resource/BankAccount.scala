package Wallet.Resource


import org.springframework.data.annotation.Id;
import scala.beans.BeanProperty
import javax.validation.constraints.NotNull
class BankAccount{
  
 
@Id
@BeanProperty
 protected var baId: Int = _


@BeanProperty
  protected var userId: Int = _

@BeanProperty 
  protected  var account_name: String = _


@BeanProperty
  @NotNull(message="routing_number is a mandatory field. It cannot be null.")
  protected  var routing_number: String = _



@BeanProperty
 @NotNull(message="account_number is a mandatory field. It cannot be null.")
  protected  var account_number: String = _




  
}