package com.probit.parkapp.common;

public interface Callback {
    interface OnSuccess {
        public void run(Object data);
    }
    interface OnFailure {
        public void run(Object data);
    }
}
