# **CMPE 195A/B - Senior Design Project I & II**

## **Team Overview**

This project was developed as part of the **Senior Design courses (CMPE-195A/B)** for the **B.S. in Software Engineering program** at **San Jose State University**. The project spanned two semesters:
- **CMPE-195A (Fall 2020):** Focused on project planning, research, and prototyping.
- **CMPE-195B (Spring 2021):** Emphasized implementation, testing, and delivery.

### **Team Members**
- [Rohan Patel](https://www.linkedin.com/in/rohanbhadreshpatel/)
- [Himmat Singh](https://www.linkedin.com/in/himmat-singh-11921a133/)
- [Harmanbir Ghotra](https://www.linkedin.com/in/harman-bir-singh-ghotra-299b58165/)
- [Inderjit Singh](https://www.linkedin.com/in/inderjitsingh1/)

# **MovInfo - Personalized Movie Discovery App 🎬**

**MovInfo** is an Android application designed to simplify movie discovery. Built with **Kotlin**, powered by **TMDb API** and **MongoDB**, it provides a seamless experience for searching movies, watching trailers, and receiving personalized recommendations—all through a clean and user-friendly interface.

## **Demo Video**
Check out this demo showcasing MovInfo's features:  
[![MovInfo Demo Video](https://img.youtube.com/vi/aunLLGuOHco/0.jpg)](https://youtu.be/aunLLGuOHco)

## **Features**

- **Search Movies**: Access a global database with diverse genres and languages.  
- **Watch Trailers**: Preview movies with integrated trailers.  
- **Personalized Recommendations**: Get tailored suggestions based on your watch history.  
- **Movie Info**: View detailed summaries, cast details, and ratings.  
- **Explore Globally**: Discover movies from different regions and languages.

## **Architecture**

MovInfo employs a **Model-View-ViewModel (MVVM)** architecture to ensure scalability, maintainability, and modularity. This design separates business logic, UI, and data handling, making the app testable and robust.

### **Core Components**
- **Model**: Handles business logic and fetches movie data from TMDb API and MongoDB.  
- **View**: Renders the user interface and observes changes from the ViewModel.  
- **ViewModel**: Mediates between the Model and View, exposing observable data streams to the View.  

### **Design Patterns**
- Single Activity  
- Model-View-ViewModel (MVVM)  
- Single Source of Truth (SSOT)  
- Repository Pattern (Local Cache & Remote Data Source)  

<p align="center">
  <img src="images/architecture_diagram.png" alt="MovInfo Architecture Diagram" width="600"/>
</p>

## **🛠️ Technologies**

### **Core Stack**
- **Frontend**: Kotlin, Android Studio  
- **Backend**: TMDb API, MongoDB  

### **Libraries**
- [Dagger2](https://github.com/google/dagger) - Dependency injection  
- [Retrofit](https://square.github.io/retrofit/) - API communication  
- [Glide](https://github.com/bumptech/glide) - Image loading  
- [Lottie](https://github.com/airbnb/lottie-android) - Animation framework  

### **Prototyping & Management Tools**
- [Marvel App](https://marvelapp.com/) - Wireframing and prototyping  
- [Taiga](https://www.taiga.io/) - Agile project management

## **🏁 Getting Started**

### **Prerequisites**
- Android Studio 4.2+  
- Android SDK 29+  
- Gradle 6.x+  
- Git 3.x+  

### **Installation**
1. Clone the repository:  
   ```sh
   git clone https://github.com/rohanbpatel14/MovInfo.git
   ```  
2. Configure your credentials for [TMDb API](https://developers.themoviedb.org/3/getting-started/introduction) and [MongoDB](https://www.mongodb.com/docs/drivers/android/).  
3. Open the project in **Android Studio**, build it, and run it on an Android emulator or device.

## **Development Process**

Our team followed Agile methodologies, using Taiga to track progress and manage sprints. Development involved the following iterative stages:
1. Requirements gathering and research  
2. UI prototyping and architecture design  
3. Implementation of front-end and back-end components  
4. Testing, debugging, and documentation  

## **Acknowledgments**

We extend our gratitude to:  
- **Dr. Jahan Ghofraniha**: For his invaluable guidance.  
- **Professor Wencen Wu**: For her support throughout the course.  
- **Friends and Family**: For participating in our research surveys.  
- **The Movie Database (TMDb) API**: For providing comprehensive movie data. 