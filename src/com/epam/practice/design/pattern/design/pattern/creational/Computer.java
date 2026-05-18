package com.epam.practice.design.pattern.design.pattern.creational;
public class Computer {
    // Final fields to ensure immutability
    private final String HDD;
    private final String RAM;
    private final boolean isGraphicsCardEnabled;
    private final boolean isBluetoothEnabled;

    // Private constructor so only the Builder can instantiate it
    private Computer(Builder builder) {
        this.HDD = builder.HDD;
        this.RAM = builder.RAM;
        this.isGraphicsCardEnabled = builder.isGraphicsCardEnabled;
        this.isBluetoothEnabled = builder.isBluetoothEnabled;
    }

    // Static nested Builder class
    public static class Builder {
        private String HDD;
        private String RAM;
        private boolean isGraphicsCardEnabled;
        private boolean isBluetoothEnabled;

        // Constructor for required parameters
        public Builder(String hdd, String ram) {
            this.HDD = hdd;
            this.RAM = ram;
        }

        // Setter methods for optional parameters (return 'this' for chaining)
        public Builder setGraphicsCardEnabled(boolean isGraphicsCardEnabled) {
            this.isGraphicsCardEnabled = isGraphicsCardEnabled;
            return this;
        }

        public Builder setBluetoothEnabled(boolean isBluetoothEnabled) {
            this.isBluetoothEnabled = isBluetoothEnabled;
            return this;
        }

        // Final build method to return the target object
        public Computer build() {
            return new Computer(this);
        }
    }
}
