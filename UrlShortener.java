import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UrlShortener {
    private final String bucket = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Map<String, String> shortTolong = new HashMap<>();
    private final Map<String, String> longToShort = new HashMap<>();
    private final int limit;
    private final Random random = new Random();

    public UrlShortener(int limit) {
        this.limit = limit;
    }

    public String createShortUrl(String longUrl) {
        if (longToShort.containsKey(longUrl)) {
            return longToShort.get(longUrl);
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < limit; i++) {
            sb.append(bucket.charAt(random.nextInt(bucket.length())));
        }
        if (shortTolong.containsKey(sb.toString())) {
            return createShortUrl(longUrl);
        }
        longToShort.put(longUrl, sb.toString());
        shortTolong.put(sb.toString(), longUrl);
        return sb.toString();
    }
    public String getLongUrl(String shortUrl) {
        return shortTolong.get(shortUrl);
    }
}
