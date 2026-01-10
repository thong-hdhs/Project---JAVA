package com.example.projectcrud.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "project_teams")
public class ProjectTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "project_team_members",
        joinColumns = @JoinColumn(name = "project_team_id"),
        inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> members;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}