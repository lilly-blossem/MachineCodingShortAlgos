public class SlidingWindowRateLimiter {
    private final long windowLimit;
    private final long allowedLimit;
    private final Deque<Long> req;

    public SlidingWindowRateLimiter(long windowLimit, long allowedLimit) {
        this.windowLimit = windowLimit;
        this.allowedLimit = allowedLimit;
        this.req = new ArrayDeque<>();
    }

    public synchronized boolean isAllow(){
        long now = System.currentTimeMillis();

        while (!req.isEmpty() && now - req.getFirst() >= windowLimit){
            req.pollFirst();
        }
        if(req.size()==allowedLimit){
            return false;
        }
        req.addLast(now);
        return true;
    }
}
