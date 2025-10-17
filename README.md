[NOT MAINTAINED]
Learners India: The Future of Collaborative Education

Project Overview

Learners India is a comprehensive, multi-role mobile learning platform designed to connect students, teachers, and parents in a secure and engaging educational environment. Our goal is to foster collaborative learning, provide rich video content, and ensure transparent communication, maximizing student success.

The application is built for the Android platform and utilizes a dedicated user authentication system for three distinct roles: Student, Teacher, and Parent.

Key Features by Role

The platform offers a unique set of tools and functionalities tailored to each user type:

1. Teacher Features

Teachers are the content creators and facilitators, responsible for delivering educational materials and managing the classroom.

Video Content Upload: Teachers can easily upload educational videos directly to the platform for assigned classes or topics.

Content Management: Tools to organize, categorize, edit, and retire uploaded video resources.

Student Progress Monitoring: Overview dashboards to track which students have viewed content and participated in related activities.

Private Chat: Ability to initiate 1:1 chat sessions with individual students for personalized guidance and support.

2. Student Features

Students are the primary users of the learning materials and collaborate with their peers.

Video Access: Seamless access to all video content uploaded by their assigned teachers.

Collaborative Learning: An integrated social/collaboration space (e.g., discussion forums or comment sections) attached to each video, allowing students to ask questions and discuss topics with classmates.

Private Chat: The ability to engage in 1:1 chat sessions with their teacher for direct questions and clarifications.

3. Parent Features

Parents are the key stakeholders in monitoring and supporting their child's academic journey.

Real-time Monitoring: Access to comprehensive reports showing their child's engagement, video viewing history, and interaction metrics.

Direct Communication (One-Way): The primary focus is monitoring. Parents can observe the teacher-student chat logs related to their child for transparency (Note: Parent-Teacher and Parent-Student chat capability may be a future feature, as the current scope focuses on 1:1 student-teacher chat and parent monitoring).

Technology Stack

Component

Technology / Language

Purpose

Mobile Platform

Android (Kotlin / Java)

Primary application development

Backend Services

Firebase Authentication

User role management and secure login

Database

Firestore (NoSQL)

Real-time data sync for chat, progress tracking, and content indexing

Storage

Firebase Storage

Hosting and delivery of uploaded video files

Networking

Retrofit/OkHttp (if external APIs are used)

API communication and data handling

Getting Started

Follow these steps to set up and run the "Learners India" project locally.

Prerequisites

Android Studio (Latest Stable Version)

Android SDK (Target API Level 33+)

A connected Firebase Project with Firestore and Authentication enabled.

Installation

Clone the Repository:

git clone [YOUR_REPO_URL]
cd learners-india-android


Configure Firebase:

Place your google-services.json file into the app/ module root of the project.

Ensure your Firebase Security Rules are configured to allow read/write access based on the authenticated user's role (student, teacher, parent).

Build and Run:

Open the project in Android Studio.

Sync Gradle files.

Select your preferred emulator or physical device.

Click the Run button (Green Play Icon).

Future Enhancements

Dedicated Parent-Teacher communication module.

Quizzing and assessment tools integrated with video content.

Offline content viewing for students.

Advanced analytics dashboards for teachers.

Contact

For support, feature requests, or contributions, please contact:

[Your Name/Team Name]

Email: [Your Contact Email]

Project Link: [Link to your official project page or repository]
