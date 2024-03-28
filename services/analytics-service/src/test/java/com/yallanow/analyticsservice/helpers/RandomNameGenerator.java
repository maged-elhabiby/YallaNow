package com.yallanow.analyticsservice.helpers;
import java.util.Random;

public class RandomNameGenerator {
    private static final String[] ADJECTIVES = {
            "Amazing", "Breathtaking", "Incredible", "Spectacular", "Marvelous",
            "Enchanting", "Stunning", "Fabulous", "Magnificent", "Wonderful"
    };
    private static final String[] EVENT_NOUNS = {
            "Concert", "Conference", "Gala", "Summit", "Festival",
            "Symposium", "Expo", "Fair", "Retreat", "Workshop"
    };
    private static final String[] GROUP_NOUNS = {
            "Club", "Society", "Association", "Collective", "Circle",
            "Alliance", "Federation", "Union", "Coalition", "Network"
    };
    private static final String[] USER_NOUNS = {
            "Explorer", "Creator", "Pioneer", "Enthusiast", "Visionary",
            "Adventurer", "Navigator", "Innovator", "Strategist", "Champion"
    };

    private static final Random random = new Random();

    public static String generateRandomEventName() {
        return generateRandomName(EVENT_NOUNS);
    }

    public static String generateRandomGroupName() {
        return generateRandomName(GROUP_NOUNS) + " " + generateRandomName(EVENT_NOUNS);
    }

    public static String generateRandomUsername() {
        return generateRandomName(USER_NOUNS) + random.nextInt(100);
    }

    private static String generateRandomName(String[] nouns) {
        String adjective = RandomNameGenerator.ADJECTIVES[random.nextInt(RandomNameGenerator.ADJECTIVES.length)];
        String noun = nouns[random.nextInt(nouns.length)];
        return adjective + " " + noun;
    }
}
