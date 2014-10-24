package Wallet.Resource



import org.springframework.data.annotation.Id;
import scala.beans.BeanProperty
import javax.validation.constraints.NotNull
import java.util.Date

class IDCard{
  
 
@Id
@BeanProperty
 protected var cardId: Int = _




@BeanProperty
  protected var userId: Int = _



@BeanProperty
  @NotNull(message="card_name is a mandatory field. It cannot be null.")
  protected var card_name: String = _


@BeanProperty
@NotNull(message="card_number is a mandatory field. It cannot be null.")
  protected var card_number: String = _






  @BeanProperty
 protected var expiration_date : String = _




  
}