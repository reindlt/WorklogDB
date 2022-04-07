package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity public class Backlog implements Serializable {
    @Id @GeneratedValue
    private Long id;
    private String vision;
    private String description;

    @OneToMany(mappedBy = "backlog", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserStory> userStories = new HashSet<>();

    public Backlog() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    public void addUserStory(UserStory userStory) {
        if (userStory == null) {
            throw new IllegalArgumentException("NULL story");
        }
        if (userStory.getBacklog() != null) {
            userStory.getBacklog().getUserStories().remove(userStory);
        }
        this.userStories.add(userStory);
        userStory.setBacklog(this);
    }

    @Override
    public String toString() {
        return description;
    }
    }
