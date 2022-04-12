# MasterMindGame

LinkedIn Reach Coding Challenge - Mastermind Game

## Game Overview
The computer is thinking of 4 numbers.  It's up to the user to guess those numbers, in the correct sequence, within 10 tries.

## Game Requirements
- Android Studio
- An emulator or Android phone

## Installation
- Download or clone this repository by clicking on the green button and choosing download zip or copy the link provided
- Upload to Android Studio by:
   - If you downloaded the zip, unzip the package then open Android Studio and choose File->Open->Choose the unzipped project
   - If you copied the link, choose File->New->Project from version control then paste in the link
- Run on emulator or android device:
   - If you are running on an emulator, download one in Android Studio by following these [instructions](https://www.alphr.com/run-android-emulator/).
   - If you are running on an Android phone, follow these [instructions](https://guides.codepath.com/android/Running-Apps-on-Your-Device).

## Issues

I was able to create most of the required functionality for the game.  I ran into an issue with getting the numbers from the API
and parsing the numbers in order to display them as a string or list.  If I could get the numbers as a string or list I would be
able to then compare each number of the user's guess to the random number. Because I spent majority of my time on this issue, 
I wasn't able to add better UI designs and more game options for the user. As of now the game doesn't recognize the correct guess for 
all 4 digits, however if you enter single digits the computer will alert you if that digit is in the random number.


