package com.project.jarjamediaapp.Utilities;

public class GetMonth {

    String previewMonth = "";

    public void getMonth(int month) {

        switch (month) {
            case 0: {
                previewMonth = "January";
            }
            break;
            case 1: {
                previewMonth = "February";
            }
            break;
            case 2: {
                previewMonth = "March";
            }
            break;
            case 3: {
                previewMonth = "April";
            }
            break;
            case 4: {
                previewMonth = "May";
            }
            break;
            case 5: {
                previewMonth = "June";
            }
            break;
            case 6: {
                previewMonth = "July";
            }
            break;
            case 7: {
                previewMonth = "August";
            }
            break;
            case 8: {
                previewMonth = "September";
            }
            break;
            case 9: {
                previewMonth = "October";
            }
            break;
            case 10: {
                previewMonth = "November";
            }
            break;
            case 11: {
                previewMonth = "December";
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + month);

        }
    }

}
