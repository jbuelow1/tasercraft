 /*
 * TASERCRAFT
 * ==========
 * Tases you when you take damage in minecraft
 * what am i doing with my life
 */

//Build parameters
#define serialBaudRate 115200

#define taserPin 8

#define ledActivePin 12
#define ledConnectionPin 13

#define buzzPin 10

void setup() {
  Serial.begin(serialBaudRate);

  pinMode(taserPin, OUTPUT);

  pinMode(ledActivePin, OUTPUT);
  pinMode(ledConnectionPin, OUTPUT);

  pinMode(buzzPin, OUTPUT);

  startupTones();

  boolean connection = false;
  while (!connection) {
    if (Serial.available()) {
      char val = Serial.read();
      if (val == 't') {
        Serial.println("TASERCRAFT");
        connectionTones();
        Serial.println("TASER READY");
        connection = true;
        digitalWrite(ledConnectionPin, HIGH);
      }
    }
  }
}

void loop() {
  if (Serial.available() >= 2) {
    if (Serial.read() == 't') {      
      int power = Serial.read();
      Serial.println("ok");
      tase(power);
    } else {
      while (Serial.available()) {
        Serial.read();
      }
    }
  }
}

void tase(int p) {
  digitalWrite(ledActivePin, HIGH);
  //set DAC to appropriate level
  digitalWrite(taserPin, HIGH);
  delay(5);
  digitalWrite(taserPin, LOW);
  delay(10);
  digitalWrite(ledActivePin, LOW);
}

void startupTones() {
  tone(buzzPin, 800, 100);
  delay(100);
  tone(buzzPib, 1000, 100);
  delay(100);
  tone(buzzPin, 1200, 250);
  delay(250);
}

void connectionTones() {
  
}
