package com.crossover.medical.journals.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "journals")
@NamedQueries({ @NamedQuery(name = "Journal.findAll", query = "SELECT j FROM Journal j ORDER BY j.id DESC") })
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty
    @Enumerated(EnumType.STRING)
    @Column(name = "topic", nullable = false)
    private Topic topic;

    @NotEmpty
    @JsonProperty
    @Column(name = "subject", nullable = false)
    private String subject;

    @NotEmpty
    @JsonProperty
    @Column(name = "author", nullable = false)
    private String author;

    @NotNull
    @JsonProperty
    @Column(name = "year", nullable = false)
    private Integer year;

    @NotEmpty
    @JsonProperty
    @Column(name = "filename", nullable = false)
    private String filename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
