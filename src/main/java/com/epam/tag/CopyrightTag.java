package com.epam.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Calendar;

public class CopyrightTag extends TagSupport {
    private String year;

    @Override
    public int doStartTag() throws JspException {
        try {
            String result = "&copy;BeautySalon " + year;
            pageContext.getOut().write(result);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    public void setYear(String year) {
        this.year = year;
        String current = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        if (!current.equalsIgnoreCase(year)) {
            this.year += " – " + current;
        }
    }
}
