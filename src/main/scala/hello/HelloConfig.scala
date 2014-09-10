package hello

import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


/**
 * This config class will trigger Spring @annotation scanning and auto configure                                                                                         Spring context.
 *
 * @author saung
 * @since 1.0
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan
@RestController
@RequestMapping(value=Array("/"))
class HelloConfig {

@RequestMapping(method=Array(RequestMethod.GET))
@ResponseBody
def respondtorequest(): String = {
return "Hello World"


}
}
