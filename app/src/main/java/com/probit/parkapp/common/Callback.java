package com.probit.parkapp.common;

public interface Callback {
    interface OnSuccess<T> {
        public void run(T data);
    }
    interface OnFailure {
        public void run(Object data);
    }
}
