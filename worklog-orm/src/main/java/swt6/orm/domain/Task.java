package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Task implements Serializable {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private int points;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    @ManyToOne
    private UserStory userStory;

    public Task() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public void addLogBookEntry(LogbookEntry logbookEntry) {
        if (logbookEntry == null) {
            throw new IllegalArgumentException("NULL logBookEntry");
        }
        if (logbookEntry.getTask() != null) {
            logbookEntry.getTask().getLogbookEntries().remove(logbookEntry);
        }
        this.logbookEntries.add(logbookEntry);
        logbookEntry.setTask(this);
    }
}
