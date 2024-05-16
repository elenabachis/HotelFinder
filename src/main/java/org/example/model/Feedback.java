package org.example.model;

public class Feedback {
    private int userId;
    private int hotelId;
    private int servicesRating;
    private int cleanlinessRating;
    private String comments;

    public Feedback(int userId, int hotelId, int servicesRating, int cleanlinessRating, String comments) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.servicesRating = servicesRating;
        this.cleanlinessRating = cleanlinessRating;
        this.comments = comments;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getServicesRating() {
        return servicesRating;
    }

    public void setServicesRating(int servicesRating) {
        this.servicesRating = servicesRating;
    }

    public int getCleanlinessRating() {
        return cleanlinessRating;
    }

    public void setCleanlinessRating(int cleanlinessRating) {
        this.cleanlinessRating = cleanlinessRating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "userId=" + userId +
                ", hotelId=" + hotelId +
                ", servicesRating=" + servicesRating +
                ", cleanlinessRating=" + cleanlinessRating +
                ", comments='" + comments + '\'' +
                '}';
    }
}
