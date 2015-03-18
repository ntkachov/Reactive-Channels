package com.ntkachov.channel.policy;

/**
 * Created by Nick on 3/16/15.
 */
public class ChannelPolicyException extends RuntimeException {
    public ChannelPolicyException() {
    }

    public ChannelPolicyException(String message) {
        super(message);
    }

    public ChannelPolicyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelPolicyException(Throwable cause) {
        super(cause);
    }

    public ChannelPolicyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
