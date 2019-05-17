package com.z.medicinedispensary.models;

//delete, not used anywhere

public class Error {
    private String error;

    public Error() {
    }

    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Error{" +
                "error='" + error + '\'' +
                '}';
    }
}
