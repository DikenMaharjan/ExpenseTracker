# Sample app to track expenses.

## Running the app
  There is a simple server in the project that holds user in memory and provides auth functionalities. **The OTPs are not sent to the user but logged in the server console.** 
  To start the server,
  run the following commands from the project's root directory,
  ```
   cd server
   npm install
   npm start
  ```
## Configuring the app
  Provide an implementation of 
  ```com.example.network.baseurl.BaseUrlProvider``` 
  to specify the base url of the server.
  The default implementation is 
  ```com.example.network.baseurl.EmulatorLocalHostBaseUrlProvider``` for running the app in emulator which points to the localhost of the host machine (loopback address).

## Basic features of app
  - Contains authentication functionalites with OTPs generated from the server (The OTPs are printed in the server console)
  - Stores user details in an encrypted form using AndroidKeyStore.
   - Allows adding new categories.
  - Allows adding and viewing all expenses.
  - Change the themes.

  
  
