# ElifeCertify

ElifeCertify is an Android application designed to generate life certificates for pensioners. It simplifies the process of creating and verifying life certificates using advanced biometric authentication methods like fingerprint and face recognition.

## Key Features

#### Admin Login
- Admin can register pensioners by entering their PPO number, which fetches their details via an API.

#### Multi-Step Registration
- Pensioner registration involves four steps:
  1. **Entering PPO number** to fetch pensioner details.
  2. **Capturing the pensioner's face** for facial recognition.
  3. **Taking the fingerprint** for biometric verification.
  4. **Capturing Aadhar front-back photo** and **pensioner photo**.

#### Pensioner Login
- Pensioners log in using their registered phone number and OTP verification.

#### Life Certificate Generation
- Pensioners can **view or generate** their life certificate.

#### Verification Methods
- For certificate generation, pensioners can choose between:
  - **Fingerprint recognition** or 
  - **Face recognition** for verification.

#### Questionnaire
- Pensioners must answer two questions:
  1. Are you remarried? If yes, provide details (when and where).
  2. Are you retired but working in another organization? If yes, provide details (when and which organization).

#### Certificate Delivery
- The generated **life certificate link** is sent via SMS to the pensioner's mobile number.

#### Admin Dashboard
- Admin can view and search all registered pensioners by their **PPO number**.

## Usage

#### Admin
1. Log in using admin credentials.
2. Register a new pensioner by entering their PPO number.
3. Complete the registration steps:
   - Capture face photo.
   - Capture fingerprint.
   - Capture Aadhar front-back photo and pensioner photo.
4. View and search pensioners by their PPO number.

#### Pensioner
1. Log in with the registered phone number and OTP verification.
2. If the life certificate has already been generated, view it.
3. To generate a new life certificate:
   - Choose the method for verification (fingerprint or face recognition).
4. Answer the questions about remarriage and employment status.
5. Receive the life certificate link via SMS once it is generated.

## Installation

To run this app locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/username/repository-name.git
   ```
2. Open the project in Android Studio.
3. Build and run the project on an Android emulator or device.

## Dependencies
- Android SDK (min SDK version: 21)
- Volley: For API requests and network communication.
- MFS100 SDK: For fingerprint recognition and biometric verification.
- Android Biometric API: For face recognition.
