package com.example.Appointment.Review;

public class DoctorReviewDTO {
    private Integer rating;
    private String comments;
    private String userName; 

    public DoctorReviewDTO(Integer rating, String comments, String userName) {
        this.rating = rating;
        this.comments = comments;
        this.userName = userName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
