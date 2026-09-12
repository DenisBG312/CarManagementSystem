public class Car {
    private String regNumber;
    private String make;
    private String model;
    private int registrationYear;
    private double engineVolume;

    public Car(String regNumber, String make, String model,
               int registrationYear, double engineVolume) {
        this.regNumber = regNumber;
        this.make = make;
        this.model = model;
        this.registrationYear = registrationYear;
        this.engineVolume = engineVolume;
    }

    public String getRegNumber()     { return regNumber; }
    public String getMake()          { return make; }
    public String getModel()         { return model; }
    public int getRegistrationYear() { return registrationYear; }
    public double getEngineVolume()  { return engineVolume; }

    @Override
    public String toString() {
        return regNumber + " | " + make + " " + model + " | " + registrationYear + " г. | " + engineVolume + " л";
    }
}