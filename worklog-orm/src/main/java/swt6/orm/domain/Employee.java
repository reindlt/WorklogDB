package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.cfg.NotYetImplementedException;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "employee",
               cascade = CascadeType.ALL,
               fetch = FetchType.EAGER,
               orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private Set<Project> projects = new HashSet<>();

    public Employee() {}

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public void detach() {
        for (LogbookEntry entry : new ArrayList<>(getLogbookEntries())) {
            this.logbookEntries.remove(entry);
        }
    }

    public void addLogbookEntry(LogbookEntry entry) {
        if (entry.getEmployee() != null) {
            entry.getEmployee().getLogbookEntries().remove(entry);
        }
        this.logbookEntries.add(entry);
        entry.setEmployee(this);
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return this.logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> entries) {
        this.logbookEntries = entries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        project.getMembers().add(this);
        this.projects.add(project);
    }

    public void removeProject(Project project) {
        if (project.getMembers() != null) {
            project.getMembers().remove(this);
        }
        this.projects.remove(project);
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuffer sb = new StringBuffer();
        sb.append(id + ": " + lastName + ", " + firstName + " (" + dateOfBirth.format(formatter) + ")");

        if (this.logbookEntries.size() > 0) {
            sb.append("\n  ");
            for(LogbookEntry e : this.logbookEntries){
             sb.append(e.toString());
             sb.append("\n  ");
            }
        }

        if (address != null) {
            sb.append(", " + address);
        }

        return sb.toString();
    }
}