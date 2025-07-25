package com.strawberry.springblog;

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import jakarta.persistence.GeneratedValue;

@Entity
@Table(name = "Posts")
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "PostId", columnDefinition = "INT")
    @JsonProperty("id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AuthorId", nullable = false, columnDefinition = "INT")
    @JsonProperty("authorId")
    private User author; 

    @Column(name = "Title", nullable = false, columnDefinition = "NVARCHAR(100)")
    @JsonProperty("title")
    @NotBlank
    private String title;

    @Column(name = "Content", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    @JsonProperty("content")
    @NotBlank
    private String content;

    @Column(name = "DateCreated", nullable = false, columnDefinition = "DATETIME2")
    @JsonProperty("dateCreated")
    private LocalDateTime dateCreated;

    @Column(name = "LastUpdated", nullable = false, columnDefinition = "DATETIME2")
    @JsonProperty("lastUpdated")
    private LocalDateTime lastUpdated;

    // private static int postCount = 0;

    public Post() {
        this.id = 0; // default constructor for JSON deserialization (doesn't increment id counter)
    }

    public Post(User author, String title, String content) {
      this.author = author;
      this.title = title;
      this.content = content;
      this.dateCreated = LocalDateTime.now();
      this.lastUpdated = LocalDateTime.now();
      // postCount++;
      // this.postId = postCount;
    }

    // public static void resetPostCount() { // When blog is wiped (admin gets deleted), # of posts must be reset
    //   postCount = 0;
    // }

    public int getId() {
      return this.id;
    }

    public String getTitle() {
      return title;
    }

    public String getContent() {
      return content;
    }

    public User getAuthor() {
      return this.author;
    }

    public String getFormattedTimeStamp(LocalDateTime dateObj) {
      // Create a DateTimeFormatter obj
      DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
      // Format timestamp into month dd, YYYY, timestamp
      return dateObj.format(formatter);
    }

    private void updateTimeStamp() { 
      this.lastUpdated = LocalDateTime.now();
    }

    public String getDateCreated() {
      return getFormattedTimeStamp(dateCreated);
    }

    public String getLastUpdated() {
      return getFormattedTimeStamp(lastUpdated);
    }

    public void setTitle(String title) {
      this.title = title;
      updateTimeStamp();
    }

    public void addContent(String additionalContent) { 
      this.content += "\n" + additionalContent;
      updateTimeStamp();
    }

    public void changeContent(String newContent) {
      this.content = newContent;
      updateTimeStamp();
    }

    public void preview(){
      System.out.println(this.title.toUpperCase() + "\n" + 
                        this.author.getUsername() + " | #" + this.id + " | " + getLastUpdated());
    }

    public String toString() {
      return this.title.toUpperCase()  + "\n" + 
      this.author.getUsername() +  " | #" + this.id + " | " + getLastUpdated() + 
      "\n\n" + content;
    }
}