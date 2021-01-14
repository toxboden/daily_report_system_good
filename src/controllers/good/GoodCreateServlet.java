package controllers.good;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Good;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsCreateServlet
 */
@WebServlet("/good/create")
public class GoodCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Good r = new Good();
        List<Good>goods  = em.createNamedQuery("getGoodCount", Good.class)
                .setParameter("report", (Report) em.find(Report.class, Integer.parseInt(request.getParameter("id"))))
                .setParameter("employee", (Employee) request.getSession().getAttribute("login_employee"))
                .getResultList();
        r.setEmployee((Employee) request.getSession().getAttribute("login_employee"));
        r.setReport((Report) em.find(Report.class, Integer.parseInt(request.getParameter("id"))));
        if (goods.size() == 0 ) {
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
        }else{
            //消したいgoodの情報消す
            Good good= goods.get(0);
            em.getTransaction().begin();
            em.remove(good);       // データ削除
            em.getTransaction().commit();
        }
        em.close();

        request.getSession().setAttribute("flush", "登録が完了しました。");

        response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getReport().getId());
    }
}
