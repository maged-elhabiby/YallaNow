package com.yallanow.analyticsservice.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.yallanow.analyticsservice.AnalyticsServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Random;

@SpringBootTest(classes = {AnalyticsServiceApplication.class, PubSubTestConfig.class})
public class PubSubItemTest {

    @Autowired
    private PubSubTestConfig.TestPubsubOutboundGateway messagingGateway;

    @Autowired
    private PubSubTemplate pubSubTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testPubSubMessageProcessing() throws Exception {
        Event event = generateRandomEvent();
        String eventJson = objectMapper.writeValueAsString(event);
        System.out.println(eventJson);
        messagingGateway.sendToPubsub(eventJson);

        Thread.sleep(5000); // Adjust the sleep time as needed for your application

    }

    private Event generateRandomEvent() {
        Random random = new Random();
        return new Event(
                "ADD",
                String.valueOf(random.nextInt()),
                String.valueOf(random.nextInt()),
                generateRandomName("group"),
                generateRandomName("event"),
                "Random Event Description",
                "2023-01-01T00:00:00Z",
                "2023-01-01T02:00:00Z",
                new Location(random.nextInt(), random.nextInt(200), "Random City", "Random Province", "Random Country"),
                random.nextInt(100),
                random.nextInt(200),
                "active",
                "Random Image Url"
        );
    }

    private static final String[] ADJECTIVES = {
            "Amazing", "Breathtaking", "Incredible", "Spectacular", "Marvelous",
            "Enchanting", "Stunning", "Fabulous", "Magnificent", "Wonderful"
    };
    private static final String[] NOUNS = {
            "Concert", "Conference", "Gala", "Summit", "Festival",
            "Symposium", "Expo", "Fair", "Retreat", "Workshop"
    };
    private static final String[] GROUP_NAMES = {
            "Club", "Society", "Association", "Collective", "Circle",
            "Alliance", "Federation", "Union", "Coalition", "Network"
    };

    public static String generateRandomName(String type) {
        Random random = new Random();
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];

        String noun;
        if ("event".equalsIgnoreCase(type)) {
            noun = NOUNS[random.nextInt(NOUNS.length)];
        } else if ("group".equalsIgnoreCase(type)) {
            noun = GROUP_NAMES[random.nextInt(GROUP_NAMES.length)];
        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }

        return adjective + " " + noun;
    }


    // Event class to represent your schema
    public class Event {
        public String getOperation() {
            return operation;
        }

        private String operation;
        private String eventId;
        private String groupId;
        private String groupName;
        private String eventTitle;
        private String eventDescription;
        private String startTime;
        private String endTime;
        private Location location;
        private Integer attendeeCount;
        private Integer capacity;
        private String status;
        private String imageUrl;

        public Event(String operation, String eventId, String groupId, String groupName, String eventTitle, String eventDescription, String startTime, String endTime, Location location, Integer attendeeCount, Integer capacity, String status, String imageUrl) {
            this.operation = operation;
            this.eventId = eventId;
            this.groupId = groupId;
            this.groupName = groupName;
            this.eventTitle = eventTitle;
            this.eventDescription = eventDescription;
            this.startTime = startTime;
            this.endTime = endTime;
            this.location = location;
            this.attendeeCount = attendeeCount;
            this.capacity = capacity;
            this.status = status;
            this.imageUrl = imageUrl;
        }

        public String getEventId() {
            return eventId;
        }

        public String getGroupId() {
            return groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getEventTitle() {
            return eventTitle;
        }

        public String getEventDescription() {
            return eventDescription;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public Location getLocation() {
            return location;
        }

        public Integer getAttendeeCount() {
            return attendeeCount;
        }

        public Integer getCapacity() {
            return capacity;
        }

        public String getStatus() {
            return status;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
    static class Location {
        private Integer addressID;
        private Integer street;
        private String city;
        private String province;
        private String country;

        public Location(Integer addressID, Integer street, String city, String province, String country) {
            this.addressID = addressID;
            this.street = street;
            this.city = city;
            this.province = province;
            this.country = country;
        }

        public Integer getAddressID() {
            return addressID;
        }

        public Integer getStreet() {
            return street;
        }

        public String getCity() {
            return city;
        }

        public String getProvince() {
            return province;
        }

        public String getCountry() {
            return country;
        }
    }

}
