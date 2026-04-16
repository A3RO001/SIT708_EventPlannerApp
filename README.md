# Event Planner App (SIT708 Task 4.1)

## Overview
This is an Android Event Planner application developed using Kotlin. The app allows users to create, view, update, and delete events while ensuring proper validation and data persistence using Room Database.

---

## Features

### CRUD Operations
- Create events with Title, Category, Location, and Date/Time
- View all upcoming events in a list
- Update event details
- Delete events with confirmation

---

### Data Persistence
- Uses Room Database for local storage
- Data remains even after app restart

---

### Navigation
- Implemented using Fragments
- Buttons used to switch between:
  - Add Event screen
  - View Events screen

---

### Validation & Error Handling
- Prevents empty Title and Date fields
- Blocks selection of past dates and times
- Displays feedback using Toast messages

---

## Technical Implementation

###  Architecture
- Fragment-based UI
- Room Database (Entity, DAO, Database)

###  Key Components
- `AddEventFragment` → Handles event creation
- `EventListFragment` → Displays and manages events
- `EventDao` → Database operations
- `AppDatabase` → Database instance

---

## Technologies Used
- Kotlin
- Android Studio
- Room Persistence Library
- DatePickerDialog & TimePickerDialog

---

## Event Sorting
- Events are stored in `yyyy-MM-dd HH:mm` format
- Automatically sorted in ascending order by date and time

---

## Demonstration
The application demonstrates:
- Creating events
- Editing events
- Deleting events
- Validation checks
- Persistent storage

---

## Notes
- Date validation ensures no past events can be added or updated
- Edit functionality allows modification of all fields

---

## Author
Anay Jayakumar
Student ID: 224726304
