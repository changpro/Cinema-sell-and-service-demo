package cinema.servlet;

import cinema.bean.Timetable;
import cinema.dao.MovieDao;
import cinema.dao.MovieHallDao;
import cinema.dao.SeatDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

@WebServlet("/modifyTimeTable")
public class ManageTimetableServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        String id = request.getParameter("inputId");
        if(id!=null){
            if (request.getParameter("box")!=null){
                if (new SeatDao().resetReserved(new MovieHallDao().getTimetableById(id))){
                    request.setAttribute("message", "已清空全部订票~");
                    request.getRequestDispatcher("showTimetable.jsp").forward(request,response);
                    return;
                }
            }
            if (new MovieHallDao().deleteTimetableById(id)){
                response.sendRedirect(request.getContextPath()+String.format("/root.jsp?message=%s", URLEncoder.encode("删除档期成功！", StandardCharsets.UTF_8)));
            }
        }
        else{
            if (new MovieDao().addMovie(request.getParameter("inputMName"))){

                Timetable tt = new Timetable();
                tt.setMName(request.getParameter("inputMName"));
                tt.setMhName(session.getAttribute("movieHall").toString());
                tt.setShowTime(Timestamp.valueOf(request.getParameter("inputStartTime")));
                tt.setEndTime(Timestamp.valueOf(request.getParameter("inputEndTime")));
                tt.setPrice(Double.parseDouble(request.getParameter("inputPrice")));
                System.out.println(tt.getMName());
                if (new MovieHallDao().addTimetable(tt)){
                    response.sendRedirect(request.getContextPath()+String.format("/root.jsp?message=%s", URLEncoder.encode("添加档期成功！", StandardCharsets.UTF_8)));
                }
            }
        }
    }
}
