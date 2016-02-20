package com.luckcheese.wheretoeat.model;

import java.util.List;

public class Wrapper {

    public static class Contact {
        private String phone;
        private String formattedPhone;
        private String twitter;
        private String facebook;
        private String facebookUsername;
        private String facebookName;

        public String getPhone() {
            return phone;
        }

        public String getFormattedPhone() {
            return formattedPhone;
        }

        public String getTwitter() {
            return twitter;
        }

        public String getFacebook() {
            return facebook;
        }

        public String getFacebookUsername() {
            return facebookUsername;
        }

        public String getFacebookName() {
            return facebookName;
        }
    }

    public static class Location {
        private String address;
        private String crossStreet;
        private double lat;
        private double lng;
        private int distance;
        private String postalCode;
        private String cc;
        private String city;
        private String state;
        private String country;
        private List<String> formattedAddress;

        private String fullAddress;

        public String getAddress() {
            return address;
        }

        public String getCrossStreet() {
            return crossStreet;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public int getDistance() {
            return distance;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public String getCc() {
            return cc;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getCountry() {
            return country;
        }

        public List<String> getFormattedAddress() {
            return formattedAddress;
        }

        public String getFullAddress() {
            if (fullAddress == null) {
                StringBuilder builder = new StringBuilder();
                for (String line : formattedAddress) {
                    builder.append(line);
                    builder.append('\n');
                }
                fullAddress = builder.toString();
            }
            return fullAddress;
        }
    }
}
