package com.strawberry.springblog;

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import jakarta.validation.constraints.*;

public class Post{
    private int authorId; 
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private LocalDateTime LastEditedTimeStamp;
    private static int postCount = 0;
    @NotNull
    private final int postID;

    public Post() {
        this.postID = 0; // default constructor for JSON deserialization (doesn't increment id counter)
    }
    public Post(int authorId, String title, String content) {
      this.authorId = authorId;
      this.title = title;
      this.content = content;
      this.LastEditedTimeStamp = LocalDateTime.now();
      postCount++;
      this.postID = postCount;
    }

    public static void resetPostCount() { // When blog is wiped (admin gets deleted), # of posts must be reset
      postCount = 0;
    }

    public int getID() {
      return postID;
    }

    public String getTitle() {
      return title;
    }

    public String getContent() {
      return content;
    }

    public int getAuthorId() {
      return this.authorId;
    }

    public String getFormattedTimeStamp() {
      // Create a DateTimeFormatter obj
      DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
      // Format timestamp into month dd, YYYY, timestamp
      return this.LastEditedTimeStamp.format(formatter);
    }

    private void updateTimeStamp() { 
      this.LastEditedTimeStamp = LocalDateTime.now();
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
                        "AuthorId: " + this.authorId + " | #" + this.postID + " | " + getFormattedTimeStamp());
    }

    public String toString() {
      return this.title.toUpperCase()  + "\n" + 
      "AuthorId: " + this.authorId +  " | #" + this.postID + " | " + getFormattedTimeStamp() + 
      "\n\n" + content;
    }
}