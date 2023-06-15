package com.tictactoe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession();
        Field field = extractField(currentSession);
        int index = getSelectedIndex(req);
        field.getField().put(index, Sign.CROSS);

        List<Sign> data = field.getFieldData();

        currentSession.setAttribute("data", data);
        currentSession.setAttribute("field", field);

        resp.sendRedirect("/index.jsp");
    }

    private Field extractField(HttpSession currentSession) {
        Object fieldAttr = currentSession.getAttribute("field");
        if (Field.class != fieldAttr.getClass()) {
            currentSession.invalidate();
            throw new RuntimeException("Session is broken, try one more time");
        }
        return (Field) fieldAttr;
    }

    private int getSelectedIndex(HttpServletRequest request) {
        String click = request.getParameter("click");
        boolean isNumber = click.chars().allMatch(Character::isDigit);
        return isNumber ? Integer.parseInt(click) : 0;
    }
}
