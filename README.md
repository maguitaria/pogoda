# Weather App - Mobile Development course 2024

This Android app displays current weather information based on user input or location.
It utilizes the OpenWeatherMap API to fetch weather data.
### The app follows MVVM architecture.
-----

<img src="https://github.com/maguitaria/pogoda/assets/112544437/0a874b01-4fee-46cb-aad1-00c1ec8c8755" width="200" height="400" />
<img src="https://github.com/maguitaria/pogoda/assets/112544437/aaddd459-0577-4d79-9953-9ddcdebf0104" width="200" height="400"/>
<img src="https://github.com/maguitaria/pogoda/assets/112544437/27962195-8c88-4a62-85a7-fe8f5c48a70f"width="200" height="400" />
<img src="https://github.com/maguitaria/pogoda/assets/112544437/eee258d1-9c8f-49e0-9f75-363948ea41e9" width="200" height="400" />
<img src="https://github.com/maguitaria/pogoda/assets/112544437/094c87ce-b017-4f88-bbc1-5239bdcfb757" width="200" height="400" />

## Features

- **Software Logic:** The app includes software logic to retrieve and display current weather data.
- **ViewModel Class:** Implemented ViewModel class to manage the UI state and functionality.
- **Mobile UI:** Modified the theme, colors, and layout to create a mobile-friendly UI.
- **API Integration:** The app uses the OpenWeatherMap API and Retrofit API builder to fetch and display weather data.
- **MVVM Architecture:** Followed MVVM architecture to organize the code into separate files and packages.
- **Error Handling:** Properly handles errors, displaying a message if the API response is empty or encounters an error.
- **Loading Indicator:** Displays a message or spinner when data is being loaded in the background.
- **Image Display:** Utilizes the Coil library to display weather-related images.
- **Navigation:** Implemented navigation between at least two screens (main and info).
- **Info Screen:** Added a simple info screen.
- - **Resource Files:** All strings are saved under the resource file for easy localization.
## Configuration

1. **OpenWeatherMap API Key:**
   - Obtain a free API key from [OpenWeatherMap](https://openweathermap.org/api) by signing up.
### Create an assets folder
Add env (no dot) to the assets folder.
Insert variable API_KEY with given string value from the website of OpenWeather API
Configure dotenv to search /assets for a file with name env
```
val dotenv = dotenv {
directory = "/assets"
filename = "env" // instead of '.env', use 'env'
}
dotenv["API_KEY"]
```
Use link to make the process easier
Note: The above configuration is required because dot files in /assets do not appear to resolve on Android. (Seeking recommendations from the Android community on how dotenv-kotlin configuration should work in order to provide the best experience for Android developers)
## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/weather-app.git
   ```

2. Open the project in Android Studio.

3. Configure OpenWeatherMap API Key as mentioned in the Configuration section.

4. Build and run the app on an Android emulator or physical device.

## Additional Notes

- Ensure you have an active internet connection to fetch weather data.

Feel free to extend the app with additional features or improvements based on your creativity and learning objectives!

### Android Usage
