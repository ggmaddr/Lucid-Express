# Lucid Express - Your Lucid Dreaming Companion  

## Project Overview  
Lucid Express is an Android application designed to assist users in achieving **lucid dreaming** through structured techniques, a **personal dream journal**, a **REM sleep alarm**, and a **calm music player** for relaxation.

## Technical Information  
- **Platform**: Android OS  
- **Programming Language**: Java  
- **Build System**: Gradle  
- **Minimum API Level**: 29 (Android 10)  
- **Development Tools**: Android Studio, XML for UI design  
- **Local Data Storage**: SharedPreferences for user settings and SQLite for journal entries  
- **Alarm Functionality**: Uses Android’s `AlarmManager` and `WakefulBroadcastReceiver` to wake the user at optimal times  
- **Music Player**: Custom media service utilizing `MediaPlayer` for background playback  
- **Offline Support**: No internet connection required; all data is stored locally  

## Features  
- **Lucid Dreaming Alarm**: Helps users wake up during REM sleep to increase the chances of lucid dreaming.  
- **Dream Journal**: Allows users to log, save, and revisit dreams, improving dream recall.  
- **Calm Music Player**: Plays relaxing tracks for sleep enhancement and meditation.  
- **Lucid Dreaming Tutorials**: Provides guidance on techniques like WBTB, MILD, FILD, and WILD.  
- **Reality Check Methods**: Encourages awareness exercises to increase dream lucidity.  

## User Guide  

### Home Screen  
The home screen provides access to the core features: **Alarm, Dream Journal, and Tutorials**.  

### REM Sleep Alarm  
Users can set an alarm to wake up at optimal times during sleep cycles. The alarm includes manual time selection and AM/PM toggling.  

### Dream Journal  
Users can write and save dream entries locally. Journals are accessible for review to enhance dream recall.  

### Calm Music Player  
A built-in media player allows users to listen to soothing music. Users can control playback, switch tracks, and adjust volume.  

### Lucid Dreaming Tutorials  
The tutorial section includes explanations of popular lucid dreaming techniques and tips for increasing success.  

## Why Use Lucid Express?  
- Enhances dream recall and awareness  
- Improves sleep quality with calming sounds  
- Guides users with scientifically supported lucid dreaming methods  
- Works offline with secure local storage  

## Installation & Setup  
1. **Clone or download** the repository.  
2. **Open the project in Android Studio** and sync Gradle dependencies.  
3. **Run the app** on an emulator or physical device (API 29+).  

