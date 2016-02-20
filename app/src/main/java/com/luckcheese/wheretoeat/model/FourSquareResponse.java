package com.luckcheese.wheretoeat.model;

import java.util.List;

public class FourSquareResponse {

    private Meta meta;
    private Response response;

    public Meta getMeta() {
        return meta;
    }

    public Response getResponse() {
        return response;
    }

    public static class Meta {
        private int code;
        private String requestId;
    }

    public static class Response {
        private int suggestedRadius;
        private String headerLocation;
        private String headerFullLocation;
        private String headerLocationGranularity;
        private String query;
        private int totalResults;
        private GeoPosition.Bounds suggestedBounds;
        private List<Group> groups;

        public int getSuggestedRadius() {
            return suggestedRadius;
        }

        public String getHeaderLocation() {
            return headerLocation;
        }

        public String getHeaderFullLocation() {
            return headerFullLocation;
        }

        public String getHeaderLocationGranularity() {
            return headerLocationGranularity;
        }

        public String getQuery() {
            return query;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public GeoPosition.Bounds getSuggestedBounds() {
            return suggestedBounds;
        }

        public List<Group> getGroups() {
            return groups;
        }
    }

    public static class Group {
        private String type;
        private String name;
        private List<GroupItem> items;

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public List<GroupItem> getItems() {
            return items;
        }
    }

    public static class GroupItem {
        private Venue venue;

        public Venue getVenue() {
            return venue;
        }
    }

    public static class Venue {
        private String id;
        private String name;
        private Wrapper.Contact contact;
        private Wrapper.Location location;
        private boolean verified;
        private String url;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Wrapper.Contact getContact() {
            return contact;
        }

        public Wrapper.Location getLocation() {
            return location;
        }

        public boolean isVerified() {
            return verified;
        }

        public String getUrl() {
            return url;
        }
    }
}
