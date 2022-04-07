package swt6.orm.domain;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Feature extends Task implements Serializable {
    private LocalDate dueDate;
    private Status status;

    public Feature() {
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
