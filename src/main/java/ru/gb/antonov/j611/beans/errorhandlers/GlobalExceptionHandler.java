package ru.gb.antonov.j611.beans.errorhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*   Этот класс-бин используется для обработки исключений, бросаемых в приложении. С его помощью
необрабатываемые исключения «превращаются» в сообщения и отправляются клиенту.
*/
@ControllerAdvice   //< наследуется от @Component
public class GlobalExceptionHandler
{

    //это искл-е использ-ся hibernate.validator'ом.
    @ExceptionHandler
    public ResponseEntity<?> catchOurValidationException (OurValidationException e)
    {
        return new ResponseEntity<>(new ErrMsg (e.getMessages()), HttpStatus.BAD_REQUEST);
    }
}
