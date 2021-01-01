package com.project.jarjamediaapp.Utilities;

/**
 * Created by Akshay Kumar on 12/24/2020.
 */
public class CallingClass extends SecurityManager {
    public static final CallingClass INSTANCE = new CallingClass();

    public Class[] getCallingClasses() {
        return getClassContext();
    }
}
