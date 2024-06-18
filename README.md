# Gallery
> Gallery is an Android application demonstrating the usage of the `Decompose` architecture with `MVIKotlin`, `Reaktive` and `Coil`.
> This project focuses on handling navigation and state management in a structured manner, providing an example of integrating image fetching from the internet, disk, and resources.
## Features
  - **Multi-screen Navigation**: Navigate between different screens using Decompose's stack navigation.
  - **State Management**: Manage UI state using MVIKotlin.
  - **Reactive Programming**: Utilize Reaktive for handling asynchronous operations.
  - **Image Handling**: Fetch images from the internet, save them to disk, and load them into the application.
  - **SVG Support**: Download and display SVG images.
## Architecture
> This project follows an architecture using Decompose and MVIKotlin for managing navigation and state. The key components are:
  - **RootComponent**: Manages the navigation and composition of screens.
  - **FirstScreenComponent**, **SecondScreenComponent**, **ThirdScreenComponent**: Handle the business logic and state for each respective screen.
  - **Stores**: Used for state management, leveraging MVIKotlin.
  - **Repositories**: Handle data fetching from various sources (internet, disk, resources).
## Prerequisites
  - Android Studio
  - Kotlin 1.5+
  - Gradle
## Installation
  1. Clone this repository to your local machine.
    `git clone https://github.com/M7mdSh3banX/Gallery.git`
  2. Open the project in Android Studio.
  3. Sync the project with Gradle.
## Preview
https://github.com/M7mdSh3banX/Gallery/assets/93403099/55afe319-d037-47a8-8723-291dcc5b3426
## Usage
> The application consists of three main screens:
  - **FirstScreen**: Displays a list of images in `JPEG` and `SVG` formats retrieved from the Internet.
  - **SecondScreen**: Displays a list of images fetched from the internet in `JPEG` and `SVG` formats, saved to disk and displayed using Coil.
  - **ThirdScreen**: Displays a list of images in `JPEG` and `SVG` formats retrieved from Android resources.
## Navigation
  - Navigation between screens is handled by `RootComponent`. The navigation stack is managed using `Decompose's StackNavigation`.
## State Management
> State for each screen is managed using MVIKotlin stores. Each store consists of:
  - **Bootstrapper**: Initializes the store.
  - **Executor**: Handles business logic and asynchronous operations.
  - **Reducer**: Reduces messages to new states.
## Dependencies
> Handled using version catalog
  - *Decompose*
  - *MVIKotlin*
  - *Reaktive*
  - *Coil*

## Contribution Guidelines
If you'd like to contribute to this project, please follow these guidelines:

- Fork the repository.
- Create a new branch (`git checkout -b feature/feature-name`).
- Implement your changes.
- Test your changes thoroughly.
- Commit your changes (`git commit -m 'add new feature'`).
- Push to the branch (`git push origin feature/feature-name`).
- Create a new Pull Request.

## Support
  - If you encounter any issues while using this project, please create an issue on the [issues](https://github.com/M7mdSh3banX/Gallery/issues) page.

## License
- This project is licensed under the Apache License 2.0 - see the [LICENSE](https://github.com/M7mdSh3banX/Gallery/blob/master/LICENSE) file for details.
