# SYSC3303 — Multithreaded Elevator Control System

A multithreaded elevator control system simulation in Java. Models Floor, Scheduler, and Elevator subsystems communicating via synchronized buffers, with a state-machine scheduler, fault detection, and a real-time GUI.

---

## System Overview

```
Floor (Thread)
    ↓  reads input file → puts requests into buffer
Scheduler (Thread)
    ↓  routes requests between Floor and Elevator
Elevator (Thread)
    ↓  processes jobs: move → open doors → load → move → unload
```

All communication between subsystems uses synchronized `Box` buffers to ensure thread safety.

---

## Project Structure

```
src/
├── Main.java              # Entry point — initializes and starts all threads
├── Floor.java             # Reads input file, sends requests to Scheduler
├── Scheduler.java         # State machine — routes requests between Floor and Elevator
├── Elevator.java          # Processes elevator requests, simulates movement
├── ElevatorRequest.java   # Data structure for a single elevator request
├── Box.java               # Synchronized bounded buffer for thread communication
├── BoxStub.java           # Unsynchronized stub used in unit tests
├── ElevatorTest.java      # JUnit tests for Elevator class
└── Input_file             # Sample elevator requests

Documentation/
├── UML_Class_Diagram.png
├── UML_Sequence_Diagram.png
└── Sequence.png
```

---

## Class Descriptions

**`Main.java`**
Initializes all shared `Box` buffers, creates and starts the `Floor`, `Scheduler`, and `Elevator` threads.

**`Floor.java`**
Reads elevator requests line by line from `src/Input_file` on startup. The `run()` method sends each request to the Scheduler with a 1-second delay between submissions.

**`Scheduler.java`**
Implements a state machine with four states: `IDLE → newRequest → sendElevator → complete`. Receives requests from Floor, forwards them to the Elevator, and sends completions back to Floor.

**`Elevator.java`**
Runs as a thread. On receiving a request, simulates: moving to the source floor → opening doors → waiting for passengers → closing doors → moving to destination → opening doors → unloading. Each step includes a timed delay.

**`ElevatorRequest.java`**
Immutable data class holding: timestamp, source floor, direction (`UP`/`DOWN`), destination floor, and elevator number.

**`Box.java`**
Thread-safe bounded buffer using `wait()`/`notifyAll()`. Holds one `ElevatorRequest` at a time. `put()` blocks if full, `get()` blocks if empty.

**`BoxStub.java`**
Extends `Box` without synchronization. Used only in unit tests to avoid blocking behavior.

---

## Input File Format

Located at `src/Input_file`. Each line represents one elevator request:

```
HH:MM:SS.s  SourceFloor  Direction  DestinationFloor  ElevatorNumber
```

Example:
```
14:05:15.0  2  Up  4  1
14:05:16.0  4  Down  1  1
```

---

## Setup

**Prerequisites**
- Java Development Kit (JDK) 8 or later
- IntelliJ IDEA (recommended) or any Java IDE
- JUnit 4 (for running tests)

**Steps**
1. Clone or download the repository
2. Open the project in IntelliJ
3. Build the project — no path changes needed, `Input_file` is referenced relatively

---

## Running

Run the classes in this order:

```
1. ElevatorManager
2. Scheduler
3. Floor
```

Or simply run `Main.java` to start all threads together. The GUI will appear automatically and the terminal will show each elevator action as it happens.

---

## Testing

Tests are written with **JUnit 4** in `ElevatorTest.java`.

**`testCheckElevator`**
Verifies that an elevator correctly accepts a request assigned to its elevator number and rejects others.

**`testElevatorMovementDirection`**
Simulates an UP request (floor 1 → 4) and a DOWN request (floor 4 → 1) and asserts the elevator ends up on the correct destination floor.

Run tests from IntelliJ by right-clicking `ElevatorTest.java` → Run.

---

## Performance

Processing all requests in the sample input file takes approximately **45 seconds**, measured using `System.currentTimeMillis()` in the Timer class.

---

## Known Limitations

- Error handling stops the system on socket exceptions rather than recovering gracefully
- The scheduling algorithm is basic — no load balancing across multiple elevators
- The GUI is display-only and does not support user interaction

---

## License

This project was developed as a course assignment for **SYSC 3303 — Real-Time Concurrent Systems** in the Department of Systems and Computer Engineering at **Carleton University**. It is intended for educational purposes only and is not licensed for commercial use.
