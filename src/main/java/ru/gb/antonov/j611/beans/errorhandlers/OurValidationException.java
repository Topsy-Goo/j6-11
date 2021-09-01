package ru.gb.antonov.j611.beans.errorhandlers;

import java.util.Collections;
import java.util.List;

//Сласс создан для использования возможностей hibernate.validator'а.
public class OurValidationException extends RuntimeException
{
    private final List<String> messages;    //< см. GlobalExceptionHandler и ErrMsg

    public OurValidationException (List<String> strings)
    {
        messages = strings;
    }

    public List<String> getMessages() {   return Collections.unmodifiableList (messages);   }
}
