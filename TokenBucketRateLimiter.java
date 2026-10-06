public class TokenBucketRateLimiter {
    private final long tokenRefillRate;
    private final long window;
    private final long maxTokens;
    private long currentToken;
    private long lastRefillTime;

    public TokenBucketRateLimiter(long tokenRefillRate, long lastRefillTime, long window, long currentToken, long maxTokens) {
        this.tokenRefillRate = tokenRefillRate;
        this.lastRefillTime = lastRefillTime;
        this.window = window;
        this.currentToken = currentToken;
        this.maxTokens = maxTokens;
    }

    public synchronized boolean allow() {

        refillToken();
        if (currentToken <= 0) {
            return false;
        }
        currentToken--;
        return true;
    }

    private void refillToken() {
        long now = System.currentTimeMillis();
        if (now - lastRefillTime >= window) {
            long elapsed = now - lastRefillTime;
            long tokensToAdd = (elapsed / window) * tokenRefillRate;
            currentToken = Math.min(maxTokens, currentToken + tokensToAdd);
            lastRefillTime = now;
        }
    }
}
