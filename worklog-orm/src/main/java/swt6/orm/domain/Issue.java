package swt6.orm.domain;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Issue extends Task implements Serializable {
    private String releaseVersion;
    private LocalDate fixedDate;
    private Severity severity;

    public Issue() {
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public LocalDate getFixedDate() {
        return fixedDate;
    }

    public void setFixedDate(LocalDate fixedDate) {
        this.fixedDate = fixedDate;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
}
