package sms;

abstract class AdminFunction {
    String adminID;
    String adminUsername;
    String enteredUsername;
    String enteredEmail;
    String enteredPassword;
    String enteredRepeatPassword;

    abstract void AdminEditProfile();

    abstract void ViewSurveys(int pageNo);

    abstract void ViewSCProfile(int pageNo);

    abstract void DeleteSCProfile();

    abstract void RegisterAdmin();

}
