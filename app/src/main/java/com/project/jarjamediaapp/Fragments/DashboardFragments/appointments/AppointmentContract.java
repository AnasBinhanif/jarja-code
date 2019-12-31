//Contract
package com.project.jarjamediaapp.Fragments.DashboardFragments.appointments;


public interface AppointmentContract {

    /*
    View - this defines the methods that the pure views like Activity or Fragments etc will implement.
    */
     interface View {
         void setupViews();
    }

    /*
    Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
    this is where the app logic is defined.
    */
     interface Actions {
         void initScreen();
    }

    /*
    this defines the methods that pure model or persistence class like database or server data will implement.
    */
     interface Repository {

    }

}
