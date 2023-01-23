package com.epam.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interface that execute a command and gives to servlet request and response
 *
 * @author Volodymyr Tytukh
 */

public interface ServletCommand {

    String execute(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
