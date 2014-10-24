package Wallet.Resource


import org.springframework.data.annotation.Id;
import scala.beans.BeanProperty
import javax.validation.constraints.NotNull

class User{
  
 
@Id
@BeanProperty
  protected var userId: Int = _





@BeanProperty
  @NotNull(message="email is a mandatory field. It cannot be null.")
  protected var email: String = _



@BeanProperty
  @NotNull(message="password is a mandatory field. It cannot be null.")
 protected  var password : String = _

@BeanProperty
  protected var name: String = _



  @BeanProperty
  protected var created_at: String = _


  @BeanProperty
  protected var updated_at: String = _




  
}