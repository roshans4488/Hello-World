package Wallet.Resource



import org.springframework.data.annotation.Id;
import scala.beans.BeanProperty
import javax.validation.constraints.NotNull

class WebLogin{
  
 
@Id
@BeanProperty
  protected var loginId: Int = _

@BeanProperty
  protected var userId: Int = _

@BeanProperty
  @NotNull(message="url is a mandatory field. It cannot be null.")
  protected  var url: String = _


@BeanProperty
  @NotNull(message="login is a mandatory field. It cannot be null.")
  protected var login: String = _



@BeanProperty
  @NotNull(message="password is a mandatory field. It cannot be null.")
  protected var password: String = _




  
}