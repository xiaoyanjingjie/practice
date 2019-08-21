package wan.dianjie.wandj.config.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * service 切面
 *
 * @author wan dianjie
 * @date 2019-08-21 16:23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogServiceFilter {
  String value() default "" ;

}
