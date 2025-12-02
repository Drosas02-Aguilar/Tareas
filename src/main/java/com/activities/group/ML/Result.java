
package com.activities.group.ML;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Result {
        @JsonIgnore
    public boolean correct;
    public int status;
    public String  errorMessage;
    public Object  object;
    public Exception ex;
}

