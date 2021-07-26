package jpa.myunjuk.infra.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {EnumValidator.class}) //annotation이 실행 할 ConstraintValidator 구현체
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER}) //메소드, 필드, 파라미터에 적용 가능
@Retention(RetentionPolicy.RUNTIME) //annotation을 Runtime까지 유지
public @interface Enum {
    String message() default "Invalid value. This is not permitted.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends java.lang.Enum<?>> enumClass();
    boolean ignoreCase() default false;
}
