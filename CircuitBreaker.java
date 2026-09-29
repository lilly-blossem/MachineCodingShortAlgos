public class CircuitBreaker {
    private State state;
    private long windowLimit;
    private long lastFailedRequestTime;
    private long totalFailedRequestCount;
    private long failedRequestThresholdCount;
    private long halfOpenThresholdCount;

    public CircuitBreaker(long windowLimit, long failedRequestThresholdCount, long halfCount) {
        this.lastFailedRequestTime = 0;
        this.state = State.CLOSE;
        this.windowLimit = windowLimit;
        this.totalFailedRequestCount = 0;
        this.failedRequestThresholdCount = failedRequestThresholdCount;
        this.halfOpenThresholdCount = halfCount;
    }

    public boolean allow() {
        return state.equals(State.CLOSE) || state.equals(State.HALF_OPEN);
    }

    public void recordSuccess() {
        if (state.equals(State.HALF_OPEN)) {
            // successful call in HALF_OPEN → circuit recovered, close it
            state = State.CLOSE;
            totalFailedRequestCount = 0;
        }
    }

    public void recordFailure() {
        long now = System.currentTimeMillis();
        // fixed window: if current failure is outside the last window, reset count
        if (now - lastFailedRequestTime > windowLimit) {
            totalFailedRequestCount = 0;
        }
        totalFailedRequestCount++;
        lastFailedRequestTime = now;

        // CLOSE -> OPEN once failures cross threshold
        if (totalFailedRequestCount >= failedRequestThresholdCount) {
            state = State.OPEN;
        }
        // OPEN -> HALF_OPEN once failures cross half-open threshold
        if (totalFailedRequestCount >= halfOpenThresholdCount) {
            state = State.HALF_OPEN;
        }
    }
    public void requestMockFlow(CircuitBreaker circuitBreaker){
        if(circuitBreaker.allow()){
            try {
                circuitBreaker.recordSuccess();
            } catch (Exception e) {
                circuitBreaker.recordFailure();
                throw new RuntimeException(e);
            }
        }
    }
}

enum State {
    OPEN,
    CLOSE,
    HALF_OPEN;
}
