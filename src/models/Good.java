package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "good")
@NamedQueries({
    @NamedQuery(
            name = "getAllGoods",
            query = "SELECT e FROM Good AS e ORDER BY e.id DESC"
            ),
    @NamedQuery(
            name = "getGoodsCount",
            query = "SELECT COUNT(e) FROM Good AS e"
            ),
    @NamedQuery(
            name = "getMyAllGoods",
            query = "SELECT r FROM Good AS r WHERE r.report = :report ORDER BY r.id DESC"
        ),
        @NamedQuery(
            name = "getMyGoodsCount",
            query = "SELECT COUNT(r) FROM Good AS r WHERE r.employee = :employee"
        ),
    @NamedQuery(
            name = "getGoodCount",
            query = "SELECT r FROM Good AS r WHERE r.employee = :employee and r.report = :report ORDER BY r.id DESC"
        )
})
@Entity
public class Good {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}