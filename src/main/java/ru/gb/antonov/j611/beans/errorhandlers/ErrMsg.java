package ru.gb.antonov.j611.beans.errorhandlers;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
//@NoArgsConstructor
public class ErrMsg
{
    private List<String> messages;
    private final Date date;

    public ErrMsg ()    {   date = new Date();   }
    public ErrMsg (List<String> strings){   this();     messages = strings;    }
    public ErrMsg (String text)         {   this (List.of(text));   }
    public ErrMsg (String ... strings)  {   this (Arrays.asList(strings));   }

    public List<String> getMessages() {   return Collections.unmodifiableList(messages);   }
}
